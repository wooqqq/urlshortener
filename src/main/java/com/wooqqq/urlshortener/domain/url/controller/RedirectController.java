package com.wooqqq.urlshortener.domain.url.controller;

import com.wooqqq.urlshortener.domain.url.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class RedirectController {

    private final UrlService urlService;

    // 정규식으로 7자리 영숫자만 매칭 - index.html 등 정적 리소스 요청이 이 핸들러에 걸리는 것을 방지
    @GetMapping("/{shortKey:[0-9a-zA-Z]{7}}")
    public ResponseEntity<Void> redirect(@PathVariable String shortKey) {
        String originalUrl = urlService.getOriginalUrl(shortKey);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }
}
