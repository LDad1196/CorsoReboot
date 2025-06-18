package com.example.demo.data.DTO;

import javax.management.relation.Role;

public class UserDTO {
    private String username;

    private Role role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
