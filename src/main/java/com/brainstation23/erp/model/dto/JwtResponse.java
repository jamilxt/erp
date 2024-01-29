package com.brainstation23.erp.model.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JwtResponse {
    private String token;
    private String refreshToken;
}
