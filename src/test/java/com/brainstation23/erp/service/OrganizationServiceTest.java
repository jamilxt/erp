package com.brainstation23.erp.service;

import com.brainstation23.erp.model.dto.CreateOrganizationRequest;
import com.brainstation23.erp.model.dto.UpdateOrganizationRequest;
import com.brainstation23.erp.persistence.entity.OrganizationEntity;
import com.brainstation23.erp.persistence.repository.OrganizationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrganizationServiceTest {
	@InjectMocks
	private OrganizationService organizationService;

	@Mock
	private OrganizationRepository organizationRepository;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@DisplayName("should create an organization")
	public void testCreateOne() {
		// given
		CreateOrganizationRequest createRequest = mock(CreateOrganizationRequest.class);
		OrganizationEntity createdEntity = mock(OrganizationEntity.class);
		UUID uuid = UUID.randomUUID();
		when(organizationRepository.save(any())).thenReturn(createdEntity);
		when(createdEntity.getId()).thenReturn(uuid);

		// when
		UUID actualUUID = organizationService.createOne(createRequest);

		// when
		Assertions.assertEquals(uuid, actualUUID);
		verify(organizationRepository, times(1)).save(any());
	}

	@Test
	@DisplayName("should update an organization with a valid id")
	public void testUpdateOneWithValidId() {
		// given
		UUID uuid = UUID.randomUUID();
		UpdateOrganizationRequest updateRequest = mock(UpdateOrganizationRequest.class);
		OrganizationEntity entity = mock(OrganizationEntity.class);
		when(organizationRepository.findById(uuid)).thenReturn(Optional.of(entity));

		// when
		organizationService.updateOne(uuid, updateRequest);

		// then
		verify(entity, times(1)).setName(updateRequest.getName());
		verify(organizationRepository, times(1)).save(entity);
	}
}
