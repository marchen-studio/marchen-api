package me.baero.core.exception;

import org.springframework.http.HttpStatusCode;

public interface ErrorCode {
    HttpStatusCode getStatusCode();
    String name();

    default String getTitle() {
        return null;
    }

    default String getDetail() {
        return null;
    }

    default MarchenException toException() {
        return new MarchenException(this);
    }
}
