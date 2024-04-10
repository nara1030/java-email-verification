package org.among.certification.domain.join;

import org.among.certification.domain.join.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JoinRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
