package com.brainstation23.erp.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ToString
@Getter
@Setter
public class CreateUserRequest {
    @NotNull
    @Schema(description = "User First Name", example = "Jon")
    private String firstName;

    @NotNull
    @Schema(description = "User Last Name", example = "Jones")
    private String lastName;

    @NotNull
    @Schema(description = "User Email", example = "jon@gmail.com")
    private String email;

    @NotNull
    @Schema(description = "User Account Balance", example = "100.00")
    private double accountBalance;

    @NotNull
    @Schema(description = "User Role", example = "ADMIN")
    private String role;

    @NotNull
    @Schema(description = "User Password", example = "x2Dyfs&9z")
    private String password;
}
