package com.wooqqq.urlshortener.domain.url.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "urls",
        indexes = {
                @Index(name = "idx_short_key", columnList = "short_key", unique = true)
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "short_key", nullable = false, length = 8, unique = true)
    private String shortKey;

    @Column(name = "original_url", nullable = false, length = 2048)
    private String originalUrl;

    @Column(name = "click_count", nullable = false)
    private long clickCount = 0;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

   @Builder
   private Url(String shortKey, String originalUrl, LocalDateTime expiresAt) {
       this.shortKey = shortKey;
       this.originalUrl = originalUrl;
       this.expiresAt = expiresAt;
   }

   public void incrementClickCount() {
       this.clickCount++;
   }

   public boolean isExpired() {
       return expiresAt != null && LocalDateTime.now().isAfter(expiresAt);
   }
}
