package com.brainstation23.erp.mapper;

import com.brainstation23.erp.model.domain.Organization;
import com.brainstation23.erp.model.dto.OrganizationResponse;
import com.brainstation23.erp.persistence.entity.OrganizationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrganizationMapper {
	Organization entityToDomain(OrganizationEntity entity);

	OrganizationResponse domainToResponse(Organization organization);
}
