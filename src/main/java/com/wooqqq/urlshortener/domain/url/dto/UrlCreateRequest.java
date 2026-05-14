package com.wooqqq.urlshortener.domain.url.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UrlCreateRequest {

    @NotBlank(message = "URL을 입력해주세요.")
    @URL(message = "올바른 URL 형식이 아닙니다.")
    private String originalUrl;

    private LocalDateTime expiresAt;
}
