package org.among.certification.domain.join;

import org.among.certification.domain.join.dto.UserRequest;
import org.among.certification.domain.join.entity.User;
import org.among.certification.domain.verify.VerifyRepository;
import org.among.certification.domain.verify.entity.Verification;
import org.among.certification.response.ErrorCode;
import org.among.certification.response.RestApiException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class JoinService {
    private final VerifyRepository verifyRepository;
    private final JoinRepository joinRepository;
    private final PasswordEncoder passwordEncoder;

    public JoinService(JoinRepository joinRepository, VerifyRepository verifyRepository, PasswordEncoder passwordEncoder) {
        this.joinRepository = joinRepository;
        this.verifyRepository = verifyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 이메일 기인증 여부 검사
     *
     * @param email {String}
     */
    public Optional<Verification> checkThatEmailIsVerified(String email) {
        Optional<Verification> verification = verifyRepository.findByEmail(email);
        if (verification.isEmpty())
            return verification;
        if (verification.get().isVerified())
            throw new RestApiException(ErrorCode.ALREADY_VERIFIED_EMAIL);
        return verification;
    }

    /**
     * 인증 메일 전송 후 가정보 저장(최초 이메일 인증)
     *
     * @param email {String}
     * @param uuid  {String}
     */
    @Transactional
    public void saveBeforeVerification(String email, String uuid) {
        // 인증 테이블 저장
        Verification verification = Verification.builder()
                .email(email)
                .uuid(uuid)
                .verified(false)
                .build();
        verifyRepository.save(verification);
        // 유저 테이블(주테이블) 저장
        User user = User.builder()
                .email(email)
                .verification(verification)
                .build();
        joinRepository.save(user);
    }

    /**
     * 인증 메일 전송 후 UUID 갱신(기존 인증 메일 발송 이력 존재)
     *
     * @param email {String}
     * @param uuid  {String}
     */
    @Transactional
    public void updateBeforeVerification(String email, String uuid) {
        Optional<Verification> optionalVerification = verifyRepository.findByEmail(email);
        if (optionalVerification.isEmpty())
            return;
        Verification verification = optionalVerification.get();
        verification.setUuid(uuid);
    }

    /**
     * 인증 메일 링크 클릭 시 메일 인증 상태 업데이트
     *
     * @param email
     * @param uuid
     */
    @Transactional
    public void updateStatusOfVerification(String email, String uuid) {
        Verification verification = verifyRepository.findByEmailAndUuid(email, uuid);
        if (Objects.isNull(verification))
            throw new RestApiException(ErrorCode.INVALID_UUID);
        verification.setVerified(true);
    }

    @Transactional
    public void saveAfterVerification(UserRequest userRequest) {
        User user = joinRepository.findByEmail(userRequest.getEmail());
        updateUser(user, userRequest);
    }

    private void updateUser(User user, UserRequest userRequest) {
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setUsername(userRequest.getUsername());
    }
}
