package me.baero.core.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MarchenException extends RuntimeException {
    private final ErrorCode errorCode;
}
