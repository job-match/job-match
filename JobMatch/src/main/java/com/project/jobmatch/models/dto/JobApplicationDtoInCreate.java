package com.project.jobmatch.models.dto;

import com.project.jobmatch.models.Skill;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Set;

public class JobApplicationDtoInCreate {

    @NotNull(message = "Min desired salary can't be empty!")
    @DecimalMin(value = "1.0", message = "Min desired salary must be positive.")
    private double minDesiredSalary;

    @NotNull(message = "Max desired salary can't be empty!")
    @DecimalMin(value = "1.0", message = "Max desired salary must be positive.")
    private double maxDesiredSalary;

    @NotNull(message = "Motivation letter cannot be empty!")
    @Size(min = 2, max = 1000, message = "Motivation letter should be between 2 and 1000 symbols!")
    private String motivationLetter;

    @NotNull(message = "Location cannot be empty!")
    @Size(min = 2, max = 50, message = "Location should be up to 50 symbols!")
    private String location;

    private Set<String> skills;


    public JobApplicationDtoInCreate() {
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

    public Set<String> getSkills() {
        return skills;
    }

    public void setSkills(Set<String> skills) {
        this.skills = skills;
    }
}
