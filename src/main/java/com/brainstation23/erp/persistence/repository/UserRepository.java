package com.brainstation23.erp.persistence.repository;

import com.brainstation23.erp.persistence.entity.Role;
import com.brainstation23.erp.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByUsername(String username);
    UserEntity findByRole(Role role);
}
