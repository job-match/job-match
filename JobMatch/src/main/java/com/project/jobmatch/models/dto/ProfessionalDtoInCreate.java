package com.project.jobmatch.models.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProfessionalDtoInCreate {

    @NotNull(message = "Username cannot be empty!")
    @Size(min = 4, max = 20, message = "Username should be between 4 and 20 symbols!")
    private String username;

    @NotNull(message = "Password cannot be empty!")
    @Size(min = 4, max = 15, message = "Password should be between 4 and 15 symbols!")
    private String password;

    @NotNull(message = "First name cannot be empty!")
    @Size(min = 4, max = 32, message = "First name should be between 4 and 32 symbols!")
    private String firstName;

    @NotNull(message = "Last name cannot be empty!")
    @Size(min = 4, max = 32, message = "Last name should be between 4 and 32 symbols!")
    private String lastName;

    @NotNull(message = "Email cannot be empty!")
    @Size(min = 8, max = 50, message = "Email should be between 8 and 50 symbols!")
    private String email;

    @NotNull(message = "Summary cannot be empty!")
    @Size(min = 50, max = 500, message = "Summary should be between 50 and 500 symbols!")
    private String summary;

    @NotNull(message = "Location cannot be empty!")
    @Size(min = 2, max = 50, message = "Location should be between 2 and 50 symbols!")
    private String location;

    public ProfessionalDtoInCreate() {
    }

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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
