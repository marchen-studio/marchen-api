package me.baero.core.exception;

import org.springframework.http.HttpStatusCode;

public interface ErrorCode {
    HttpStatusCode getStatusCode();
    String getType();
    String getTitle();
    String getDetail();
}
