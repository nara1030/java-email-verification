package org.among.certification.domain.join.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.among.certification.domain.verify.entity.Verification;

@Entity
@Table(name = "member")
@NoArgsConstructor
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;
    private String password;
    private String username;
    @OneToOne
    private Verification verification;

    @Builder
    public User(String email, Verification verification) {
        this.email = email;
        this.verification = verification;
    }
}
