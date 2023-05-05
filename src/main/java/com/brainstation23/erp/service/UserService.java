package com.brainstation23.erp.service;

import com.brainstation23.erp.exception.custom.custom.AlreadyExistsException;
import com.brainstation23.erp.exception.custom.custom.NotFoundException;
import com.brainstation23.erp.mapper.UserMapper;
import com.brainstation23.erp.model.domain.User;
import com.brainstation23.erp.model.dto.request.CreateUserRequest;
import com.brainstation23.erp.model.dto.request.UpdateUserRequest;
import com.brainstation23.erp.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {
	private static final String USER_NOT_FOUND = "User not found";
	private final UserMapper userMapper;
	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	public Page<User> getUsers(Pageable pageable) {
		return userRepository.findAll(pageable).map(userMapper::toDomain);
	}
	
	public User getUserById(UUID id) {
		var userEntity = userRepository.findById(id).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
		return userMapper.toDomain(userEntity);
	}
	
	public User getUserByUsername(String username) {
		var userEntity = userRepository.findByEmail(username).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
		return userMapper.toDomain(userEntity);
	}
	
	public User getLoggedInUser(Principal principal) {
		String username = principal.getName();
		return getUserByUsername(username);
	}

	public void createUser(CreateUserRequest request) {
		this.validateCreatingUser(request);
		var userEntity = userMapper.toEntity(request);
		String encodedPassword = encodePasswordUsingString(request.getPassword());
		userEntity.setPassword(encodedPassword);
		userRepository.save(userEntity);
	}

	private void validateCreatingUser(CreateUserRequest request) {
		boolean userExist = userRepository.existsByEmail(request.getEmail());
		if (userExist) {
			throw new AlreadyExistsException("User already exists with email " + request.getEmail() + ".");
		}
	}

	public UUID getLoggedInUserId(Principal principal) {
		String username = principal.getName();
		User user = getUserByUsername(username);
		return user.getId();
	}

	public void updateUser(UpdateUserRequest request, UUID id) {
		var userEntity = userRepository.findById(id).orElseThrow(()-> new NotFoundException(USER_NOT_FOUND));
		userEntity.setFirstName(request.getFirstName());
		userEntity.setLastName(request.getLastName());
		userEntity.setEmail(request.getEmail());
		userEntity.setRoles(request.getRoles());
		userRepository.save(userEntity);
	}

	public void updateUserPassword(String userName, String password) {
		var userEntity = userRepository.findByEmail(userName).orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
		userEntity.setPassword(password);
		userRepository.save(userEntity);
	}
	
	public void deleteUser(UUID id) {
		userRepository.deleteById(id);
	}

	public String encodePasswordUsingString(String password) {
		return passwordEncoder.encode(password);
	}
	
	public long countTotalUser() {
		return userRepository.count();
	}
}
