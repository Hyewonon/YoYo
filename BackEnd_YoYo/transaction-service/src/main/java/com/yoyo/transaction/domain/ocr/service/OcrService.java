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
import org.springframework.kafka.annotation.KafkaListener;
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
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class OcrService {
    private final OcrProducer ocrProducer;
    @Value("${naver.ocr.api-url}")
    private String apiURL;
    @Value("${naver.ocr.secret-key}")
    private String secretKey;
    private final List<CompletableFuture<TransactionDTO.MatchRelation>> matchRelationFutures = new ArrayList<>();

    public List<TransactionDTO.MatchRelation> getText(MultipartFile imageFile, Long memberId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("X-OCR-SECRET", secretKey);

        ImageInfo imageInfo = ImageInfo.builder().format("png").name(imageFile.getOriginalFilename()).build();

        NaverRequestInfo naverRequestInfo = NaverRequestInfo.builder()
                .version("V2")
                .requestId(UUID.randomUUID().toString())
                .timestamp(System.currentTimeMillis())
                .images(Collections.singletonList(imageInfo))
                .build();

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
        log.info(response.getBody());
        List<TransactionDTO.MatchRelation> responseList = parseOcrResponse(response.getBody());
        List<TransactionDTO.MatchRelation> finalResponseList = new ArrayList<>();

        for (TransactionDTO.MatchRelation transactionResponseDTO : responseList) {
            transactionResponseDTO.setMemberId(memberId);
            ocrProducer.sendMatchRelation(transactionResponseDTO);

            CompletableFuture<TransactionDTO.MatchRelation> future = new CompletableFuture<>();
            matchRelationFutures.add(future);

            try {
                TransactionDTO.MatchRelation matchRelation = future.get();
                finalResponseList.add(matchRelation);
            } catch (Exception e) {
                log.error("Kafka response: {}", e.getMessage());
            }
        }

        return finalResponseList;
    }

    public List<TransactionDTO.MatchRelation> parseOcrResponse(String jsonResponse) {
        List<TransactionDTO.MatchRelation> responses = new ArrayList<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode fieldsNode = rootNode.get("images").get(0).get("fields");

            TransactionDTO.MatchRelation transaction = new TransactionDTO.MatchRelation();
            StringBuilder descriptionBuilder = new StringBuilder();

            for (JsonNode fieldNode : fieldsNode) {
                String inferText = fieldNode.get("inferText").asText();
                JsonNode verticesArray = fieldNode.get("boundingPoly").get("vertices");

                if (verticesArray == null || !verticesArray.isArray() || verticesArray.isEmpty()) {
                    continue;
                }

                JsonNode firstVertexX = verticesArray.get(0);
                double verticesX = firstVertexX.get("x").asDouble();
                if (inferText.equals("성") || inferText.equals("명") || inferText.equals("금") || inferText.equals("액") || inferText.equals("관") || inferText.equals("계")) {
                    continue;
                }

                if (verticesX < 850) {
                    if (inferText.matches("^[가-힣]{2,6}$")) {
                        transaction.setName(inferText);
                    }
                }
                if (inferText.matches("^\\d{1,3}(,\\d{3})*(원)?$")) {
                    String cleanText = inferText.replaceAll("[^\\d]", "");
                    transaction.setAmount(Long.parseLong(cleanText));
                }
                if (verticesX > 850) {
                    if (inferText.matches("^[가-힣a-zA-Z]+$")) {
                        descriptionBuilder.append(inferText).append(" ");
                        transaction.setDescription(descriptionBuilder.toString().trim());
                        if (inferText.contains("친구")) {
                            transaction.setRelationType(RelationType.FRIEND);
                        } else if (inferText.contains("가족")) {
                            transaction.setRelationType(RelationType.FAMILY);
                        } else if (inferText.contains("직장") || inferText.contains("회사")) {
                            transaction.setRelationType(RelationType.COMPANY);
                        } else {
                            transaction.setRelationType(RelationType.ETC);
                        }
                    }
                }
                if (transaction.getName() != null && transaction.getAmount() != null && transaction.getDescription() != null) {
                    responses.add(transaction);
                    transaction = new TransactionDTO.MatchRelation();
                    descriptionBuilder.setLength(0);
                }
            }

        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return responses;
    }

    @KafkaListener(topics = "result-match-topic", concurrency = "3")
    public void getMatchResult(TransactionDTO.MatchRelation response) {
        log.info("Received MatchRelation from Kafka: {}", response);
        if (!matchRelationFutures.isEmpty()) {
            CompletableFuture<TransactionDTO.MatchRelation> future = matchRelationFutures.remove(0);
            future.complete(response);
        }
    }
}

