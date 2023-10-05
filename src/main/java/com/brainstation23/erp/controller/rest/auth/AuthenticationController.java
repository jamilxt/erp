package com.brainstation23.erp.controller.rest.auth;

import com.brainstation23.erp.model.dto.UserDTO;
import com.brainstation23.erp.model.dto.auth.AuthenticationRequest;
import com.brainstation23.erp.model.dto.auth.AuthenticationResponse;
import com.brainstation23.erp.model.dto.auth.RegisterRequest;
import com.brainstation23.erp.persistence.entity.auth.User;
import com.brainstation23.erp.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
         return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }


    @GetMapping(value = "/users")
    public List<UserDTO> getAllUsers(){
        List<UserDTO> users_id = new ArrayList<>();
        users_id = authenticationService.getAllUsers();
        return users_id;
    }

}
