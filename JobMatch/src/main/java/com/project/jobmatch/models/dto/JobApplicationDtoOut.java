package com.project.jobmatch.models.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class JobApplicationDtoOut {

    private String minDesiredSalary;
    private String maxDesiredSalary;
    private String motivationLetter;
    private String location;
    private String status;
    private String professionalName;
    private List<SkillDtoOut> skills;
    private List<JobAdDtoOut> matchRequestsList;

    public JobApplicationDtoOut() {
    }

    public String getMinDesiredSalary() {
        return minDesiredSalary;
    }

    public void setMinDesiredSalary(String minDesiredSalary) {
        this.minDesiredSalary = minDesiredSalary;
    }

    public String getMaxDesiredSalary() {
        return maxDesiredSalary;
    }

    public void setMaxDesiredSalary(String maxDesiredSalary) {
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

    public String getProfessionalName() {
        return professionalName;
    }

    public void setProfessionalName(String professionalName) {
        this.professionalName = professionalName;
    }

    public List<SkillDtoOut> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillDtoOut> skills) {
        this.skills = skills;
    }

    public List<JobAdDtoOut> getMatchRequestsList() {
        return matchRequestsList;
    }

    public void setMatchRequestsList(List<JobAdDtoOut> matchRequestsList) {
        this.matchRequestsList = matchRequestsList;
    }
}
