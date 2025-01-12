package com.uokclubmanagement.dto;

public class UserDTO {

    private String role;
    private String username;
    private String id;

    public UserDTO(String role, String username, String id) {
        this.role = role;
        this.username = username;
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
