package com.yoyo.transaction.domain.ocr.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OcrDTO {
    private MultipartFile imageFile;

    @Builder
    public OcrDTO(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

}
