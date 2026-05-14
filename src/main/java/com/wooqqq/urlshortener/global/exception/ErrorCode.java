package com.wooqqq.urlshortener.global.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    URL_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 단축 URL입니다"),
    URL_EXPIRED(HttpStatus.GONE, "만료된 단축 URL입니다"),
    INVALID_URL_FORMAT(HttpStatus.BAD_REQUEST, "올바른 URL 형식이 아닙니다"),
    INVALID_INPUT(HttpStatus.BAD_REQUEST, "잘못된 입력입니다");

    private final HttpStatus status;
    private final String message;
}
