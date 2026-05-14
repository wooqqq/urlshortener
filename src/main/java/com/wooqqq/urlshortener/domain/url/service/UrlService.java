package com.wooqqq.urlshortener.domain.url.service;

import com.wooqqq.urlshortener.domain.url.dto.UrlCreateRequest;
import com.wooqqq.urlshortener.domain.url.dto.UrlResponse;
import com.wooqqq.urlshortener.domain.url.entity.Url;
import com.wooqqq.urlshortener.domain.url.repository.UrlRepository;
import com.wooqqq.urlshortener.global.exception.BusinessException;
import com.wooqqq.urlshortener.global.exception.ErrorCode;
import com.wooqqq.urlshortener.global.util.Base62Encoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UrlService {

    private final UrlRepository urlRepository;

    @Transactional
    public UrlResponse createShortUrl(UrlCreateRequest request) {
        Url url = Url.builder()
                .shortKey("temp")
                .originalUrl(request.getOriginalUrl())
                .expiresAt(request.getExpiresAt())
                .build();

        Url saved = urlRepository.save(url);
        String shortKey = Base62Encoder.encode(saved.getId());
        saved.updateShortKey(shortKey);

        return UrlResponse.from(saved);
    }

    public String getOriginalUrl(String shortKey) {
        Url url = urlRepository.findByShortKey(shortKey)
                .orElseThrow(() -> new BusinessException(ErrorCode.URL_NOT_FOUND));

        if (url.isExpired()) {
            throw new BusinessException(ErrorCode.URL_EXPIRED);
        }

        return url.getOriginalUrl();
    }

    @Transactional
    public UrlResponse getUrlInfo(String shortKey) {
        Url url = urlRepository.findByShortKey(shortKey)
                .orElseThrow(() -> new BusinessException(ErrorCode.URL_NOT_FOUND));

        url.incrementClickCount();
        return UrlResponse.from(url);
    }
}
