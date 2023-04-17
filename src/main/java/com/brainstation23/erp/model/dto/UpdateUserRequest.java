package com.brainstation23.erp.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ToString
@Getter
@Setter
public class UpdateUserRequest {

    @NotNull
    @Schema(description = "User Name", example = "Jon Jones")
    private String name;

    @NotNull
    @Schema(description = "User Email", example = "jon@gmail.com")
    private String email;

    @NotNull
    @Schema(description = "User Role", example = "ADMIN")
    private String role;

}
