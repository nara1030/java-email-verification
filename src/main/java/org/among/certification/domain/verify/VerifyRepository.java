package org.among.certification.domain.verify;

import org.among.certification.domain.verify.entity.Verification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VerifyRepository extends JpaRepository<Verification, Long> {
    Verification findByEmailAndUuid(String email, String uuid);
    Optional<Verification> findByEmail(String email);
}
