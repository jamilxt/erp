package com.brainstation23.erp.controller.rest;

import com.brainstation23.erp.mapper.UserMapper;
import com.brainstation23.erp.model.dto.*;
import com.brainstation23.erp.service.UserService;
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

@Tag(name = "User")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "api/users")
public class UserRestController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(summary = "Get All Users")
    @GetMapping
    public ResponseEntity<Page<UserResponse>> getAll(@ParameterObject Pageable pageable) {
        log.info("Getting List of Users");
        var domains = userService.getAll(pageable);
        return ResponseEntity.ok(domains.map(userMapper::domainToResponse));
    }

    @Operation(summary = "Get Single User")
    @GetMapping("{id}")
    public ResponseEntity<UserResponse> getOne(@PathVariable UUID id) {
        log.info("Getting Details of User({})", id);
        var domain = userService.getOne(id);
        return ResponseEntity.ok(userMapper.domainToResponse(domain));
    }

    @Operation(summary = "Create Single User")
    @PostMapping
    public ResponseEntity<Void> createOne(@RequestBody @Valid CreateUserRequest createRequest) {
        log.info("Creating an User: {} ", createRequest);
        var id = userService.createOne(createRequest);
        var location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Update Single User")
    @PutMapping("{id}")
    public ResponseEntity<Void> updateOne(@PathVariable UUID id,
                                          @RequestBody @Valid UpdateUserRequest updateRequest) {
        log.info("Updating an User({}): {} ", id, updateRequest);
        userService.updateOne(id, updateRequest);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Delete Single User")
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteOne(@PathVariable UUID id) {
        log.info("Deleting an User({}) ", id);
        userService.deleteOne(id);
        return ResponseEntity.noContent().build();
    }
}
