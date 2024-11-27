package com.project.jobmatch.models.dto;

public class JobAdDtoOut {

    private String title;
    private double minSalaryBoundary;
    private double maxSalaryBoundary;
    private String description;
    private String location;
    private String status;

    public JobAdDtoOut() {
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
}
