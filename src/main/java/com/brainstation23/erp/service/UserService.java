package com.brainstation23.erp.service;

import com.brainstation23.erp.model.domain.User;
import com.brainstation23.erp.model.dto.CreateUserRequest;
import com.brainstation23.erp.model.dto.UpdateUserRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.UUID;


public interface UserService {
     Page<User> getAll(Pageable pageable);
     User getOne(UUID id);
     UUID createOne(CreateUserRequest createUserRequest);
     void updateOne(UUID id, UpdateUserRequest updateUserRequest);
     void deleteOne(UUID id);
     UserDetails loadUserByUsername(String userName);
     UserDetailsService userDetailsService();
}
