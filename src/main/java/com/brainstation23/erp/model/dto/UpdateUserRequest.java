package com.brainstation23.erp.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {
    @NotEmpty
    @Schema(description = "First Name")
    private String firstName;
    @NotEmpty
    private String userName;
    @NotEmpty
    @Schema(description = "Last Name")
    private String lastName;
    @NotEmpty
    @Email
    @Schema(description = "Email", example = "xx@xxx.xxx")
    private String email;
    @NotEmpty
    @Schema(description = "Password")
    private String password;
}
