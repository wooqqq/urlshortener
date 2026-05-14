package com.wooqqq.urlshortener.domain.url.dto;

import com.wooqqq.urlshortener.domain.url.entity.Url;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UrlResponse {

    private String shortKey;
    private String originalUrl;
    private long clickCount;
    private LocalDateTime expiresAt;
    private LocalDateTime createdAt;

    public static UrlResponse from(Url url) {
        return UrlResponse.builder()
                .shortKey(url.getShortKey())
                .originalUrl(url.getOriginalUrl())
                .clickCount(url.getClickCount())
                .expiresAt(url.getExpiresAt())
                .createdAt(url.getCreatedAt())
                .build();
    }
}
