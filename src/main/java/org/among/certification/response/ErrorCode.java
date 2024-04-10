package org.among.certification.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    ALREADY_VERIFIED_EMAIL(HttpStatus.BAD_REQUEST, "이미 가입된 이메일입니다."),
    INVALID_UUID(HttpStatus.BAD_REQUEST, "UUID값이 일치하지 않습니다. 메일 인증을 다시 진행해주세요.");

    private final HttpStatus httpStatus;
    private final String errorMessage;
}
