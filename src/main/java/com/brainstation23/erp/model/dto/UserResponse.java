package com.brainstation23.erp.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@ToString
@Getter
@Setter
public class UserResponse {

    @Schema(description = "User ID", example = "360E68E9-DCF1-11ED-AC44-6AAD28B788FE")
    private UUID id;

    @Schema(description = "User Name", example = "Jon Jones")
    private String name;

    @Schema(description = "User Email", example = "jon@gmail.com")
    private String email;

    @Schema(description = "User Role", example = "ADMIN")
    private String role;

    @Schema(description = "User Password", example = "x2Dyfs&9z")
    private String password;
}
