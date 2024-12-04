package com.project.jobmatch.models.dto;

import jakarta.validation.constraints.NotEmpty;

public class ProfessionalLoginDto {

    @NotEmpty(message = "Cannot be empty!")
    private String username;

    @NotEmpty(message = "Cannot be empty!")
    private String password;

    public ProfessionalLoginDto() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
