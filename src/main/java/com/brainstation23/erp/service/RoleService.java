package com.brainstation23.erp.service;

import com.brainstation23.erp.exception.custom.custom.NotFoundException;
import com.brainstation23.erp.mapper.RoleMapper;
import com.brainstation23.erp.model.domain.Role;
import com.brainstation23.erp.model.dto.request.CreateRoleRequest;
import com.brainstation23.erp.model.dto.request.UpdateRoleRequest;
import com.brainstation23.erp.persistence.entity.RoleEntity;
import com.brainstation23.erp.persistence.entity.UserEntity;
import com.brainstation23.erp.persistence.repository.RoleRepository;
import com.brainstation23.erp.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RoleService {
    public static final String ROLE_NOT_FOUND = "Role not found";
    private final RoleMapper roleMapper;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public Page<Role> getRoles(Pageable pageable) {
        return roleRepository.findAll(pageable).map(roleMapper::toDomain);
    }

    public List<RoleEntity> getRolesS() {
        return roleRepository.findAll();
    }

    public Role getRole(UUID id) {
        var roleEntity = roleRepository.findById(id).orElseThrow(() -> new NotFoundException(ROLE_NOT_FOUND));
        return roleMapper.toDomain(roleEntity);
    }

    public void createRole(CreateRoleRequest request) {
        var roleEntity = roleMapper.toEntity(request);
        roleRepository.save(roleEntity);
    }

    public void updateRole(UpdateRoleRequest request, UUID id) {
        var roleEntity = roleRepository.findById(id).orElseThrow(() -> new NotFoundException(ROLE_NOT_FOUND));
        roleEntity.setName(request.getName());
        roleEntity.setDescription(request.getDescription());
        roleRepository.save(roleEntity);
    }


    public void deleteRole(UUID id) {
    	var roleEntity = roleRepository.findById(id).orElseThrow(() -> new NotFoundException(ROLE_NOT_FOUND));
    	Set<UserEntity> users = roleEntity.getUsers();
    	for(UserEntity userEntity : users) {
    		userEntity.getRoles().remove(roleEntity);
    		userRepository.save(userEntity);
    	}
    	roleEntity.getUsers().removeAll(users);
        roleRepository.deleteById(id);
    }
}
