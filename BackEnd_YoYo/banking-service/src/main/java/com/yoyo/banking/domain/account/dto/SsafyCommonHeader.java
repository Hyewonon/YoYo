package com.yoyo.banking.domain.account.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SsafyCommonHeader {

    /*
     * * 회원 가입, 회원 정보 수정 요청 DTO
     * */
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {

        private String apiName; // api 이름
        private String transmissionDate; //전송일자 YYYYMMDD
        private String transmissionTime; //전송시각 HHMMSS
        @Builder.Default
        private String institutionCode = "00100"; // 기관코드 고정
        @Builder.Default
        private String fintechAppNo = "001"; // 핀테크 앱 일련 번호 고정
        private String apiServiceCode; //api 서비스 코드 = api 이름
        @Builder.Default
        private String institutionTransactionUniqueNo = makeUniqueNo(); // 기관별 거래고유번호 (20자리 난수)
        private String apiKey;
        private String userKey;

        public static Request of(String apiName, String apiKey, String userKey){
            LocalDateTime now = LocalDateTime.now();
            return Request.builder()
                    .apiName(apiName)
                    .transmissionDate(now.format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                    .transmissionTime(now.format(DateTimeFormatter.ofPattern("HHmmss")))
                    .apiServiceCode(apiName)
                    .apiKey(apiKey)
                    .userKey(userKey)
                    .build();
        }

        public static Request of(String apiName, String apiKey){
            LocalDateTime now = LocalDateTime.now();
            return Request.builder()
                          .apiName(apiName)
                          .transmissionDate(now.format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                          .transmissionTime(now.format(DateTimeFormatter.ofPattern("HHmmss")))
                          .apiServiceCode(apiName)
                          .apiKey(apiKey)
                          .build();
        }

        private static String makeUniqueNo() {
            Random rd = new Random();
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < 20; i++) {
                sb.append(rd.nextInt(10));
            }
            return sb.toString();
        }
    }
}
