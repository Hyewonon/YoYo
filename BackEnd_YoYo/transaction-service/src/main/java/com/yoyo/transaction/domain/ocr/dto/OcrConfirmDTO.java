package com.yoyo.transaction.domain.ocr.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OcrConfirmDTO {
        private Long memberId;
        private String name;
        private String relationType;
        private Long amount;
        private String description;
}
