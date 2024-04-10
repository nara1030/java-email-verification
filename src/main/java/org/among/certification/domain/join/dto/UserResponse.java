package org.among.certification.domain.join.dto;

import lombok.Getter;

@Getter
public class UserResponse {
    private final String email;
    private final String message;

    public UserResponse(String email) {
        this.email = email;
        this.message = email + " 님의 회원가입이 완료되었습니다.";
    }
}
