package com.brainstation23.erp.service;

import com.brainstation23.erp.mapper.UserMapper;
import com.brainstation23.erp.model.domain.User;
import com.brainstation23.erp.model.dto.CreateUserRequest;
import com.brainstation23.erp.model.dto.JwtResponse;
import com.brainstation23.erp.model.dto.SignInRequest;
import com.brainstation23.erp.persistence.entity.Role;
import com.brainstation23.erp.persistence.entity.UserEntity;
import com.brainstation23.erp.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    public User SignUp(CreateUserRequest createUserRequest)
    {
        var entity = new UserEntity();
        entity.setId(UUID.randomUUID())
                .setFirstName(createUserRequest.getFirstName())
                .setLastName(createUserRequest.getLastName())
                .setEmail(createUserRequest.getEmail())
                .setPassword(passwordEncoder.encode(createUserRequest.getPassword()))
                .setRole(Role.USER);
        var createdEntity =  userRepository.save(entity);
        return userMapper.entityToDomain(createdEntity);
    }

    @Override
    public JwtResponse login(SignInRequest signInRequest) {
       /* try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword())
            );
        } catch (AuthenticationException e) {
            // Handle authentication failure, e.g., throw a custom exception or log the error.
            throw new RuntimeException("Authentication failed");
        }*/

        var user = userRepository.findByUsername(signInRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        var jwt = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        return new JwtResponse(jwt, refreshToken);
    }

}
