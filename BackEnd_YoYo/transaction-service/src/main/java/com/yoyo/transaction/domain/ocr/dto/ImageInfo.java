package com.yoyo.transaction.domain.ocr.dto;

import lombok.*;

@Getter
@NoArgsConstructor
public class ImageInfo {
    private String format;
    private String name;
    @Builder
    public ImageInfo(String format, String name) {
        this.format = format;
        this.name = name;
    }
}
