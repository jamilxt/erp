package com.brainstation23.erp.service;

import com.brainstation23.erp.persistence.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;

public interface JwtService {
     String extractUsername(String token);
     String generateToken(UserDetails userDetails);
     boolean isTokenValid(String token, UserDetails userDetails);

    String generateRefreshToken(HashMap<String, Object> extraClaims, UserEntity user);
}
