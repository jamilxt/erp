package com.brainstation23.erp.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@ToString
@Getter
@Setter
public class OrganizationResponse {
	private UUID id;

	private String name;
}
