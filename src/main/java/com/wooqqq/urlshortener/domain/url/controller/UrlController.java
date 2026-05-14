package com.wooqqq.urlshortener.domain.url.controller;

import com.wooqqq.urlshortener.domain.url.dto.UrlCreateRequest;
import com.wooqqq.urlshortener.domain.url.dto.UrlResponse;
import com.wooqqq.urlshortener.domain.url.service.UrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/urls")
public class UrlController {

    private final UrlService urlService;

    @PostMapping
    public ResponseEntity<UrlResponse> createShortUrl(@RequestBody @Valid UrlCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(urlService.createShortUrl(request));
    }

    @GetMapping("/{shortKey}")
    public ResponseEntity<UrlResponse> getUrlInfo(@PathVariable String shortKey) {
        return ResponseEntity.ok(urlService.getUrlInfo(shortKey));
    }

    @GetMapping("/{shortKey}/redirect")
    public ResponseEntity<UrlResponse> redirect(@PathVariable String shortKey) {
        String originalUrl = urlService.getOriginalUrl(shortKey);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }
}
