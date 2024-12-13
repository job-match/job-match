package com.project.jobmatch.models.dto;

public class CompanyLoginDto {
    private String username;
    private String password;

    public CompanyLoginDto() {
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
}
