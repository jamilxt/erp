package com.brainstation23.erp.controller.rest;

import com.brainstation23.erp.mapper.UserMapper;
import com.brainstation23.erp.model.domain.User;
import com.brainstation23.erp.model.dto.CreateUserRequest;
import com.brainstation23.erp.model.dto.JwtResponse;
import com.brainstation23.erp.model.dto.SignInRequest;
import com.brainstation23.erp.model.dto.UserResponse;
import com.brainstation23.erp.persistence.entity.UserEntity;
import com.brainstation23.erp.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserMapper userMapper;
    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signUp(@RequestBody CreateUserRequest user)
    {
        var domain = authenticationService.SignUp(user);
        return ResponseEntity.ok(userMapper.domainToResponse(domain));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> signIn(@RequestBody SignInRequest sign)
    {
        var jwt = authenticationService.login(sign);
        log.info("user login{}",sign);
        return ResponseEntity.ok(jwt);
    }

}
