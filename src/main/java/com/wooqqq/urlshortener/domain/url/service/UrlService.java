package com.wooqqq.urlshortener.domain.url.service;

import com.wooqqq.urlshortener.domain.url.dto.UrlCreateRequest;
import com.wooqqq.urlshortener.domain.url.dto.UrlResponse;
import com.wooqqq.urlshortener.domain.url.entity.Url;
import com.wooqqq.urlshortener.domain.url.repository.UrlCacheRepository;
import com.wooqqq.urlshortener.domain.url.repository.UrlRepository;
import com.wooqqq.urlshortener.global.exception.BusinessException;
import com.wooqqq.urlshortener.global.exception.ErrorCode;
import com.wooqqq.urlshortener.global.util.ShortKeyGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UrlService {

    private final UrlRepository urlRepository;
    private final UrlCacheRepository urlCacheRepository;

    @Transactional
    public UrlResponse createShortUrl(UrlCreateRequest request) {
        String shortKey = generateUniqueShortKey();

        Url url = Url.builder()
                .shortKey(shortKey)
                .originalUrl(request.getOriginalUrl())
                .expiresAt(request.getExpiresAt())
                .build();

        return UrlResponse.from(urlRepository.save(url));
    }

    public String getOriginalUrl(String shortKey) {
        return urlCacheRepository.find(shortKey)
                .orElseGet(() -> {
                    Url url = urlRepository.findByShortKey(shortKey)
                            .orElseThrow(() -> new BusinessException(ErrorCode.URL_NOT_FOUND));

                    if (url.isExpired()) {
                        throw new BusinessException(ErrorCode.URL_EXPIRED);
                    }

                    urlCacheRepository.save(shortKey, url.getOriginalUrl());
                    return url.getOriginalUrl();
                });
    }

    @Transactional
    public UrlResponse getUrlInfo(String shortKey) {
        Url url = urlRepository.findByShortKey(shortKey)
                .orElseThrow(() -> new BusinessException(ErrorCode.URL_NOT_FOUND));

        url.incrementClickCount();
        return UrlResponse.from(url);
    }

    private String generateUniqueShortKey() {
        String shortKey;
        do {
            shortKey = ShortKeyGenerator.generate();
        } while (urlRepository.existsByShortKey(shortKey));
        return shortKey;
    }
}
