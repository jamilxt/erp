package com.brainstation23.erp.service;

import com.brainstation23.erp.model.domain.User;
import com.brainstation23.erp.model.dto.CreateUserRequest;
import com.brainstation23.erp.model.dto.JwtResponse;
import com.brainstation23.erp.model.dto.SignInRequest;
import com.brainstation23.erp.persistence.entity.UserEntity;

import java.util.List;

public interface AuthenticationService {
    User SignUp(CreateUserRequest createUserRequest);

    JwtResponse login(SignInRequest signInRequest);
}
