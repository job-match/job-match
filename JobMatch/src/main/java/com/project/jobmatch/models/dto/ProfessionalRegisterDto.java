package com.project.jobmatch.models.dto;

import jakarta.validation.constraints.NotEmpty;

public class ProfessionalRegisterDto extends ProfessionalLoginDto {

    @NotEmpty(message = "Cannot be empty!")
    private String passwordConfirmation;
    @NotEmpty(message = "Cannot be empty!")
    private String firstName;
    @NotEmpty(message = "Cannot be empty!")
    private String lastName;
    @NotEmpty(message = "Cannot be empty!")
    private String email;

    public ProfessionalRegisterDto() {
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
