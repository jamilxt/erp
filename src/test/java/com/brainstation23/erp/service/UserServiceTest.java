package com.brainstation23.erp.service;

import com.brainstation23.erp.model.dto.CreateUserRequest;
import com.brainstation23.erp.model.dto.UpdateUserRequest;
import com.brainstation23.erp.persistence.entity.UserEntity;
import com.brainstation23.erp.persistence.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create an user")
    public void testCreateOne() {
        // Given
        CreateUserRequest createUserRequest = mock(CreateUserRequest.class);
        UserEntity entity = mock(UserEntity.class);
        UUID uuid = UUID.randomUUID();
        when(userRepository.save(any())).thenReturn(entity);
        when(entity.getId()).thenReturn(uuid);

        // When
        UUID actualUUID = userService.createOne(createUserRequest);

        // Then
        Assertions.assertEquals(uuid, actualUUID);
        verify(userRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("Should update an user with a valid id")
    public void testUpdateOneWithValidId() {
        // Given
        UUID uuid = UUID.randomUUID();
        UpdateUserRequest updateUserRequest = mock(UpdateUserRequest.class);
        UserEntity entity = mock(UserEntity.class);
        when(userRepository.findById(uuid)).thenReturn(Optional.of(entity));

        // When
        userService.updateOne(uuid, updateUserRequest);

        // Then
        verify(entity, times(1)).setFirstName(updateUserRequest.getFirstName());
        verify(entity, times(1)).setLastName(updateUserRequest.getLastName());
        verify(entity, times(1)).setEmail(updateUserRequest.getEmail());
        verify(entity, times(1)).setAccountBalance(updateUserRequest.getAccountBalance());
        verify(entity, times(1)).setRole(updateUserRequest.getRole());
        verify(userRepository, times(1)).save(entity);
    }

}
