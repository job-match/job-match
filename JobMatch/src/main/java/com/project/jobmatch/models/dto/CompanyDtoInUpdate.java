package com.project.jobmatch.models.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CompanyDtoInUpdate {

    @NotNull(message = "Password cannot be empty!")
    @Size(min = 4, max = 15, message = "Password should be between 4 and 15 symbols!")
    private String password;

    @NotEmpty(message = "Name cannot be empty!")
    @Size(min = 2, max = 50, message = "Name should be between 1 and 64 symbols!")
    private String name;

    @NotEmpty(message = "Email cannot be empty!")
    @Size(min = 5, max = 50, message = "Email should be between 5 and 50 symbols!")
    private String email;

    @NotEmpty(message = "Description cannot be empty!")
    @Size(min = 10, max = 2500, message = "Description should be between 10 and 500 symbols!")
    private String description;

    @NotEmpty(message = "Location cannot be empty!")
    @Size(min = 2, max = 50, message = "Location should be between 2 and 50 symbols!")
    private String location;

    @NotEmpty(message = "Contacts cannot be empty!")
    @Size(min = 10, max = 255, message = "Contacts should be between 10 and 255 symbols!")
    private String contacts;

    public CompanyDtoInUpdate() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }
}
