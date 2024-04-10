package org.among.certification.domain.verify.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "verification")
@NoArgsConstructor
@Setter
@Getter
public class Verification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(unique = true, nullable = false)
    private String uuid;
    @Column(nullable = false)
    private boolean verified;

    @Builder
    public Verification(String email, String uuid, boolean verified) {
        this.email = email;
        this.uuid = uuid;
        this.verified = verified;
    }
}
