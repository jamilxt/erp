package com.brainstation23.erp.controller.rest;

import com.brainstation23.erp.mapper.UserMapper;
import com.brainstation23.erp.model.dto.CreateUserRequest;
import com.brainstation23.erp.model.dto.UpdateUserRequest;
import com.brainstation23.erp.model.dto.UserResponse;
import com.brainstation23.erp.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Tag(name="user")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/users")
public class UserRestController {
    private final UserServiceImpl userService;
    private final UserMapper userMapper;

    @Operation(summary = "Get All Users")
    @GetMapping
    public ResponseEntity<Page<UserResponse>> getAll(@ParameterObject Pageable pageable)
    {
        log.info("Getting List of Users");
        var users = userService.getAll(pageable);
        return ResponseEntity.ok(users.map(userMapper::domainToResponse));
    }

    @Operation(summary =  "Get Single User")
    @GetMapping("{id}")
    public ResponseEntity<UserResponse> getOne(@PathVariable UUID id)
    {
        log.info("Getting Details Of User ({})",id);
        var entity = userService.getOne(id);
        return ResponseEntity.ok(userMapper.domainToResponse(entity));
    }

    @Operation(summary = "Create New User")
    @PostMapping
    public ResponseEntity<Void> createOne(@RequestBody @Valid CreateUserRequest createUserRequest)
    {
        log.info("Creating New User ({})",createUserRequest);
        userService.createOne(createUserRequest);
        var location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Update Existing User")
    @PutMapping("{id}")
    public ResponseEntity<Void> updateOne(@PathVariable UUID id, @RequestBody @Valid UpdateUserRequest updateUserRequest)
    {
        log.info("Update Existing User ({}):{}",id,updateUserRequest);
        userService.updateOne(id,updateUserRequest);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete User")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteOne(@PathVariable UUID id)
    {
        log.info("Delete User ({})",id);
        userService.deleteOne(id);
        return ResponseEntity.noContent().build();
    }

}
