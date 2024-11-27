package com.project.jobmatch.models.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Set;

public class JobApplicationDtoOut {

    private String minDesiredSalary;
    private String maxDesiredSalary;
    private String motivationLetter;
    private String location;
    private String status;
    private String professionalName;
    private Set<String> skills;
}
