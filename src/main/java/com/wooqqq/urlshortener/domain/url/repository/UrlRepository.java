package com.wooqqq.urlshortener.domain.url.repository;

import com.wooqqq.urlshortener.domain.url.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findByShortKey(String shortKey);

    boolean existsByShortKey(String shortKey);
}
