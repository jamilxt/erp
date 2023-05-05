package com.brainstation23.erp.model.domain;

import com.brainstation23.erp.persistence.entity.RoleEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class User {
	private UUID id;
	private String firstName;
	private String lastName;
	private String email;
	private boolean isEnable;
	private String password;
	private Set<RoleEntity> roles = new HashSet<>();

}
