package com.brainstation23.erp.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class UserResponse {

    @Schema(description = "Employee ID", example = "3F41A301-25ED-4F0F-876F-7657BEABB00F")
    private UUID id;
    private String username;
    @Schema(description = "First Name")
    private String firstName;
    @Schema(description = "Last Name")
    private String lastName;
    @Schema(description = "Email", example = "xx@xxx.xxx")
    private String email;

}
