package org.among.certification.response;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestApiExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<?> restApiExceptionHandler(RestApiException e) {
        return Response.error(e.getErrorCode().getHttpStatus().value(), e.getErrorCode().getErrorMessage());
    }
}
