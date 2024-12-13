package com.project.jobmatch.models.dto;

import jakarta.validation.constraints.NotEmpty;

public class CompanyRegisterDto extends CompanyLoginDto {
    @NotEmpty(message = "Cannot be empty!")
    private String passwordConfirmation;

    @NotEmpty(message = "Cannot be empty!")
    private String name;

    @NotEmpty(message = "Cannot be empty!")
    private String email;

    public CompanyRegisterDto() {
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
