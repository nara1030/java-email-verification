package org.among.certification.response;

import org.springframework.http.ResponseEntity;

public class Response {
    public static ResponseEntity<String> error(int resultCode, String errorMessage) {
        return ResponseEntity.status(resultCode)
                .body(errorMessage);
    }

    public static <T> ResponseEntity<T> success(T result) {
        return ResponseEntity.ok()
                .body(result);
    }
}
