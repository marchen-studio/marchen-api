package me.baero.core.advice;

import lombok.extern.slf4j.Slf4j;
import me.baero.core.exception.MarchenException;
import me.baero.core.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;
import java.util.Optional;

@Slf4j
@ControllerAdvice
public class RestControllerExceptionHandler {

    @ExceptionHandler({MarchenException.class})
    public ProblemDetail handleIntendedException(MarchenException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ProblemDetail problemDetail = ProblemDetail.forStatus(errorCode.getStatusCode());
        problemDetail.setType(URI.create(errorCode.getType()));
        problemDetail.setTitle(errorCode.getTitle());
        problemDetail.setDetail(errorCode.getDetail());

        return problemDetail;
    }

    @ExceptionHandler({Exception.class})
    public ProblemDetail handleUnexpectedException(Exception exception) {
        Optional.ofNullable(exception)
                .map(Throwable::getLocalizedMessage)
                .ifPresent(log::error);
        return ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
