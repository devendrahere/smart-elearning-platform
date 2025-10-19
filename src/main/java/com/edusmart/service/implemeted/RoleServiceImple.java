package com.edusmart.service.implemeted;

import com.edusmart.dto.RoleDTO;
import com.edusmart.entity.Roles;
import com.edusmart.repository.RoleRepository;
import com.edusmart.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImple implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public RoleDTO createRole(RoleDTO roleDTO) {
        Roles role=new Roles();
        role.setName(roleDTO.getName());
        Roles saved= roleRepository.save(role);
        return mapToDTO(saved);
    }

    @Override
    public List<RoleDTO> getAllRole() {
        return roleRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private RoleDTO mapToDTO(Roles role) {
        RoleDTO dto =new RoleDTO();
        dto.setRoleId(role.getRoleId());
        dto.setName(role.getName());
        return dto;
    }
}
