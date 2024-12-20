package com.project.jobmatch.models.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class JobAdDtoUpdate {

    @NotEmpty(message = "Title cannot be empty!")
    @Size(min = 2, max = 50, message = "Title should be up to 50 symbols!")
    private String title;

    @NotNull(message = "Min Salary cannot be empty!")
    @DecimalMin(value = "1.0", message = "Minimum salary must be positive.")
    private double minSalaryBoundary;

    @NotNull(message = "Max Salary cannot be empty!")
    @DecimalMin(value = "1.0", message = "Maximum salary must be positive")
    private double maxSalaryBoundary;

    @NotEmpty(message = "Job description cannot be empty!")
    @Size(min = 2, max = 1000, message = "Description should be up to 1000 symbols!")
    private String description;

    @NotEmpty(message = "Location cannot be empty!")
    @Size(min = 2, max = 50, message = "Location should be up to 50 symbols!")
    private String location;

    @NotEmpty(message = "Status cannot be empty!")
    @Size(min = 2, max = 50, message = "Status should be between 2 and 50 symbols!")
    private String status;

    Set<String> requirements;

    public JobAdDtoUpdate() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getMinSalaryBoundary() {
        return minSalaryBoundary;
    }

    public void setMinSalaryBoundary(double minSalaryBoundary) {
        this.minSalaryBoundary = minSalaryBoundary;
    }

    public double getMaxSalaryBoundary() {
        return maxSalaryBoundary;
    }

    public void setMaxSalaryBoundary(double maxSalaryBoundary) {
        this.maxSalaryBoundary = maxSalaryBoundary;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<String> getRequirements() {
        return requirements;
    }

    public void setRequirements(Set<String> requirements) {
        this.requirements = requirements;
    }
}
