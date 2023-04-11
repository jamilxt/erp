package com.brainstation23.erp.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@ToString
@Getter
@Setter
public class OrganizationResponse {
	@Schema(description = "Organization ID", example = "3F41A301-25ED-4F0F-876F-7657BEABB00F")
	private UUID id;

	@Schema(description = "Organization Name", example = "Brain Station 23")
	private String name;

	@Schema(description = "Organization Code", example = "ORG000001")
	private String code;
}
