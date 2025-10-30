package com.edusmart.dto;

import jakarta.validation.constraints.NotBlank;

public class RoleDTO {
    private int roleId;
    @NotBlank(message = "RoleName is required")
    private String name;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
