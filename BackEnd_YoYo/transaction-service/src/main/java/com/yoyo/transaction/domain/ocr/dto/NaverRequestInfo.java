package com.yoyo.transaction.domain.ocr.dto;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
public class NaverRequestInfo {
    private String version;
    private String requestId;
    private long timestamp;
    private String lang;
    private List<ImageInfo> images;
    @Builder
    public NaverRequestInfo(String version, String requestId, long timestamp, String lang, List<ImageInfo> images) {
        this.version = version;
        this.requestId = requestId;
        this.timestamp = timestamp;
        this.lang = lang;
        this.images = images;
    }
}
