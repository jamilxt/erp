package com.brainstation23.erp.service;

import com.brainstation23.erp.exception.custom.custom.NotFoundException;
import com.brainstation23.erp.mapper.OrganizationMapper;
import com.brainstation23.erp.model.domain.Organization;
import com.brainstation23.erp.model.dto.CreateOrganizationRequest;
import com.brainstation23.erp.model.dto.UpdateOrganizationRequest;
import com.brainstation23.erp.persistence.entity.OrganizationEntity;
import com.brainstation23.erp.persistence.repository.OrganizationRepository;
import com.brainstation23.erp.util.RandomUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrganizationService {
	public static final String ORGANIZATION_NOT_FOUND = "Organization Not Found";
	private final OrganizationRepository organizationRepository;
	private final OrganizationMapper organizationMapper;

	public Page<Organization> getAll(Pageable pageable) {
		var entities = organizationRepository.findAll(pageable);
		return entities.map(organizationMapper::entityToDomain);
	}

	public Organization getOne(UUID id) {
		var entity = organizationRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(ORGANIZATION_NOT_FOUND));
		return organizationMapper.entityToDomain(entity);
	}

	public UUID createOne(CreateOrganizationRequest createRequest) {
		var entity = new OrganizationEntity();
		entity.setId(UUID.randomUUID())
				.setName(createRequest.getName())
				.setCode(this.generateOrganizationCode());
		var createdEntity = organizationRepository.save(entity);
		return createdEntity.getId();
	}

	public void updateOne(UUID id, UpdateOrganizationRequest updateRequest) {
		var entity = organizationRepository.findById(id)
				.orElseThrow(() -> new NotFoundException(ORGANIZATION_NOT_FOUND));
		entity.setName(updateRequest.getName());
		organizationRepository.save(entity);
	}

	public void deleteOne(UUID id) {
		organizationRepository.deleteById(id);
	}

	private String generateOrganizationCode() {
		return RandomUtils.generateAlphaNumeric(6).toUpperCase();
	}
}
