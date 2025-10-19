package com.edusmart.controller;

import com.edusmart.dto.RoleDTO;
import com.edusmart.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    //creating role
    //url api/roles/
    //body RoleDTO
    @PostMapping("/")
    public ResponseEntity<RoleDTO> createRole(@RequestBody RoleDTO roleDTO){
        RoleDTO role=roleService.createRole(roleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(role);
    }
    //fetching all roles
    //url api/roles/
    //
    @GetMapping("/")
    public ResponseEntity<List<RoleDTO>> getAllRole(){
        List<RoleDTO> roles=roleService.getAllRole();
        return ResponseEntity.ok(roles);
    }
}
