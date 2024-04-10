package org.among.certification.domain.verify.dto;

import lombok.Getter;

@Getter
public class VerifyResponse {
    private final String email;
    private final String message;

    public VerifyResponse(String email) {
        this.email = email;
        this.message = email + " 주소로 메일 발송을 완료하였습니다.";
    }
}
