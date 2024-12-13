package com.project.jobmatch.models.dto;

import jakarta.validation.constraints.NotEmpty;

public class CompanyRegisterDto extends CompanyLoginDto {
    @NotEmpty(message = "Cannot be empty!")
    private String passwordConfirmation;

    @NotEmpty(message = "Cannot be empty!")
    private String name;

    @NotEmpty(message = "Cannot be empty!")
    private String email;

    @NotEmpty(message = "Cannot be empty!")
    private String description;

    @NotEmpty(message = "Cannot be empty!")
    private String contacts;

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

    public @NotEmpty(message = "Cannot be empty!") String getDescription() {
        return description;
    }

    public void setDescription(@NotEmpty(message = "Cannot be empty!") String description) {
        this.description = description;
    }

    public @NotEmpty(message = "Cannot be empty!") String getContacts() {
        return contacts;
    }

    public void setContacts(@NotEmpty(message = "Cannot be empty!") String contacts) {
        this.contacts = contacts;
    }
}
