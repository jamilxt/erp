package com.brainstation23.erp.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ToString
@Getter
@Setter
public class UpdateOrganizationRequest {
	@NotNull
	@Schema(description = "Organization Name", example = "Brain Station 23")
	private String name;
}
