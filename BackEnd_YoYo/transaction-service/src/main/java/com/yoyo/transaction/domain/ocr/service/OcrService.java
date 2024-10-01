package com.yoyo.transaction.domain.ocr.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yoyo.common.kafka.dto.RelationType;
import com.yoyo.common.kafka.dto.TransactionDTO;
import com.yoyo.transaction.domain.ocr.dto.ImageInfo;
import com.yoyo.transaction.domain.ocr.dto.NaverRequestInfo;
import com.yoyo.transaction.domain.ocr.producer.OcrProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OcrService {
    private final OcrProducer ocrProducer;
    @Value("${naver.ocr.api-url}")
    private String apiURL;
    @Value("${naver.ocr.secret-key}")
    private String secretKey;

    public List<TransactionDTO.MatchRelation> getText(MultipartFile imageFile) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("X-OCR-SECRET", secretKey);

        ImageInfo imageInfo = ImageInfo.builder().format("png").name(imageFile.getOriginalFilename()).build();

        NaverRequestInfo naverRequestInfo = NaverRequestInfo.builder().version("V2").requestId(UUID.randomUUID().toString()).timestamp(System.currentTimeMillis()).images(Collections.singletonList(imageInfo)).build();

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonMessage = objectMapper.writeValueAsString(naverRequestInfo);
            body.add("message", jsonMessage);
            body.add("file", new ByteArrayResource(imageFile.getBytes()) {
                @Override
                public String getFilename() {
                    return imageFile.getOriginalFilename();
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(apiURL, HttpMethod.POST, requestEntity, String.class);
        log.info("------------------------------------------------------------------------------------------");
        List<TransactionDTO.MatchRelation> responseList = parseOcrResponse(response.getBody());
        int count = 1;
        for (TransactionDTO.MatchRelation transactionResponseDTO : responseList) {
            log.info(String.valueOf(count++));
            log.info(transactionResponseDTO.toString());
            ocrProducer.sendMatchRelation(transactionResponseDTO);
        }
        log.info("------------------------------------------------------------------------------------------");
        return responseList;
    }

    public List<TransactionDTO.MatchRelation> parseOcrResponse(String jsonResponse) {
        List<TransactionDTO.MatchRelation> responses = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode fieldsNode = rootNode.get("images").get(0).get("fields");
            TransactionDTO.MatchRelation transaction = new TransactionDTO.MatchRelation();
            for (JsonNode fieldNode : fieldsNode) {
                String inferText = fieldNode.get("inferText").asText();
                double inferConfidence = fieldNode.get("inferConfidence").asDouble();
                // 신뢰도 0.8 이상만 처리
                if (inferConfidence >= 0.8) {
                    // 관계 추출
                    if (inferText.contains("친구")) {
                        transaction.setRelationType(RelationType.valueOf("FRIEND"));
                    } else if (inferText.contains("가족")) {
                        transaction.setRelationType(RelationType.valueOf("FAMILY"));
                    } else if (inferText.contains("직장") || inferText.contains("회사")) {
                        transaction.setRelationType(RelationType.valueOf("COMPANY"));
                    }
                    // 이름 추출
                    else if (inferText.matches(".*[가-힣]+")) {
                        transaction.setName(inferText);
                    }

                    // 금액 추출
                    else if (inferText.matches("^\\d+$")) {
                        transaction.setAmount(Long.parseLong(inferText));
                    }
                }
                if (transaction.getAmount() != null && transaction.getName() != null) {
                    if (transaction.getRelationType() == null) {
                        transaction.setRelationType(RelationType.valueOf("OTHERS"));
                    }
                    responses.add(transaction);
                    transaction = new TransactionDTO.MatchRelation();
                }
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return responses;
    }
}

