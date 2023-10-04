package com.brainstation23.erp.persistence.entity.token;

import com.brainstation23.erp.persistence.entity.auth.User;
import lombok.*;

import javax.persistence.*;
import javax.persistence.JoinColumn;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TOKENS")
public class Tokens {
    @Id
    @GeneratedValue
    private Integer id;
    private String token;
    @Enumerated(EnumType.STRING)
    private TokenType tokenType;
    private boolean expired;
    private boolean revoked;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    public User user;
}
