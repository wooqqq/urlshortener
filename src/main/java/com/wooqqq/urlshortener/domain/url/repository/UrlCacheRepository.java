package com.wooqqq.urlshortener.domain.url.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UrlCacheRepository {

    private static final String KEY_PREFIX = "url:";
    private static final Duration TTL = Duration.ofHours(24);

    private final RedisTemplate<String, String> redisTemplate;

    public void save(String shortKey, String originalUrl) {
        redisTemplate.opsForValue().set(KEY_PREFIX + shortKey, originalUrl, TTL);
    }

    public Optional<String> find(String shortKey) {
        String value = redisTemplate.opsForValue().get(KEY_PREFIX + shortKey);
        return Optional.ofNullable(value);
    }
}
