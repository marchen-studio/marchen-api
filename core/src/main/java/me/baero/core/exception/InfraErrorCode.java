package me.baero.core.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
@RequiredArgsConstructor
public enum InfraErrorCode implements ErrorCode {
    PARSING_VERSION_FAILED(HttpStatus.BAD_REQUEST),
    ;
    private final HttpStatusCode statusCode;
}