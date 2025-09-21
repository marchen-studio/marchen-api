package me.baero.core.exception;

public abstract class MarchenException extends RuntimeException {
    private final ErrorCode errorCode;

    protected MarchenException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
