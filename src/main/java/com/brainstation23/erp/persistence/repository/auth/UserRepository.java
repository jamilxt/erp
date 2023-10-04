package com.brainstation23.erp.persistence.repository.auth;

import com.brainstation23.erp.persistence.entity.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
