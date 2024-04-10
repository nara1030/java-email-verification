package org.among.certification.domain.join;

import org.among.certification.api.email.EmailService;
import org.among.certification.domain.join.dto.UserRequest;
import org.among.certification.domain.join.dto.UserResponse;
import org.among.certification.domain.verify.dto.VerifyRequest;
import org.among.certification.domain.verify.dto.VerifyResponse;
import org.among.certification.domain.verify.entity.Verification;
import org.among.certification.response.ErrorCode;
import org.among.certification.response.Response;
import org.among.certification.response.RestApiException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1")
public class JoinController {
    private final JoinService joinService;
    private final EmailService emailService;

    public JoinController(JoinService joinService, EmailService emailService) {
        this.joinService = joinService;
        this.emailService = emailService;
    }

    @PostMapping("/send-email")
    public ResponseEntity<VerifyResponse> sendEmail(@RequestBody VerifyRequest verifyRequest) {
        // 기가입된 이메일 여부 확인
        String email = verifyRequest.getEmail();
        Optional<Verification> optionalVerification = joinService.checkThatEmailIsVerified(email);
        // 이메일 발송
        String uuid = emailService.send(email);
        // 인증 정보 저장 혹은 갱신
        if (optionalVerification.isEmpty()) {
            joinService.saveBeforeVerification(email, uuid);
        } else {
            joinService.updateBeforeVerification(email, uuid);
        }
        return Response.success(new VerifyResponse(email));
    }

    @GetMapping("/verify-email")
    public void verifyEmail(@RequestParam String email, @RequestParam String uuid) {
        // 업데이트
        joinService.updateStatusOfVerification(email, uuid);
        // 클라이언트(회원가입창)로 리다이렉트
    }

    @PostMapping("/join")
    public ResponseEntity<UserResponse> join(@RequestBody UserRequest userRequest) {
        joinService.saveAfterVerification(userRequest);
        return Response.success(new UserResponse(userRequest.getEmail()));
    }
}
