package com.brainstation23.erp.persistence.repository.auth;

import com.brainstation23.erp.model.dto.UserDTO;
import com.brainstation23.erp.persistence.entity.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);


    @Query(value = "select new com.brainstation23.erp.model.dto.UserDTO(firstname, lastname, email, role) from User")
    List<UserDTO> getAllUser();
}
