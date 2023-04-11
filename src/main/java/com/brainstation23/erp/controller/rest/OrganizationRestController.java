package com.brainstation23.erp.controller.rest;


import com.brainstation23.erp.mapper.OrganizationMapper;
import com.brainstation23.erp.model.dto.CreateOrganizationRequest;
import com.brainstation23.erp.model.dto.OrganizationResponse;
import com.brainstation23.erp.model.dto.UpdateOrganizationRequest;
import com.brainstation23.erp.service.OrganizationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "api/organizations")
public class OrganizationRestController {
	private final OrganizationService organizationService;
	private final OrganizationMapper organizationMapper;

	@GetMapping
	public ResponseEntity<Page<OrganizationResponse>> getAll(Pageable pageable) {
		log.info("Getting List of Organizations");
		var domains = organizationService.getAll(pageable);
		return ResponseEntity.ok(domains.map(organizationMapper::domainToResponse));
	}

	@GetMapping("{id}")
	public ResponseEntity<OrganizationResponse> getOne(@PathVariable UUID id) {
		log.info("Getting Details of Organization({})", id);
		var domain = organizationService.getOne(id);
		return ResponseEntity.ok(organizationMapper.domainToResponse(domain));
	}

	@PostMapping
	public ResponseEntity<Void> createOne(@RequestBody @Valid CreateOrganizationRequest createRequest) {
		log.info("Creating an Organization: {} ", createRequest);
		var id = organizationService.createOne(createRequest);
		var location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping("{id}")
	public ResponseEntity<Void> updateOne(@PathVariable UUID id,
			@RequestBody @Valid UpdateOrganizationRequest updateRequest) {
		log.info("Updating an Organization({}): {} ", id, updateRequest);
		organizationService.updateOne(id, updateRequest);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteOne(@PathVariable UUID id) {
		log.info("Deleting an Organization({}) ", id);
		organizationService.deleteOne(id);
		return ResponseEntity.noContent().build();
	}
}
