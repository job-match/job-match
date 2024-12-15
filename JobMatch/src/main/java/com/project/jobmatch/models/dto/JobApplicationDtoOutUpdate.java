package com.project.jobmatch.models.dto;

import java.util.Set;

public class JobApplicationDtoOutUpdate {

    private double minDesiredSalary;

    private double maxDesiredSalary;

    private String motivationLetter;

    private String location;

    private String status;

    private Set<String> skills;

    public JobApplicationDtoOutUpdate() {
    }

    public double getMinDesiredSalary() {
        return minDesiredSalary;
    }

    public void setMinDesiredSalary(double minDesiredSalary) {
        this.minDesiredSalary = minDesiredSalary;
    }

    public double getMaxDesiredSalary() {
        return maxDesiredSalary;
    }

    public void setMaxDesiredSalary(double maxDesiredSalary) {
        this.maxDesiredSalary = maxDesiredSalary;
    }

    public String getMotivationLetter() {
        return motivationLetter;
    }

    public void setMotivationLetter(String motivationLetter) {
        this.motivationLetter = motivationLetter;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<String> getSkills() {
        return skills;
    }

    public void setSkills(Set<String> skills) {
        this.skills = skills;
    }
}
