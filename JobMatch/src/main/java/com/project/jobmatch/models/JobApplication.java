package com.project.jobmatch.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.jobmatch.models.enums.JobApplicationStatus;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "job_applications")
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_application_id")
    private int id;

    @Column(name = "min_desired_salary")
    private int minDesiredSalary;

    @Column(name = "max_desired_salary")
    private int maxDesiredSalary;

    @Column(name = "motivation_letter")
    private String motivationLetter;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "status")
    private JobApplicationStatus status;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "professional_id")
    private Professional professional;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "job_applications_skills",
            joinColumns = @JoinColumn(name = "job_application_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skills;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "job_applications_job_ads",
            joinColumns = @JoinColumn(name = "job_application_id"),
            inverseJoinColumns = @JoinColumn(name = "job_ad_id")
    )
    private Set<JobAd> listOfAdMatchRequests;

    public JobApplication() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMinDesiredSalary() {
        return minDesiredSalary;
    }

    public void setMinDesiredSalary(int minDesiredSalary) {
        this.minDesiredSalary = minDesiredSalary;
    }

    public String getMotivationLetter() {
        return motivationLetter;
    }

    public void setMotivationLetter(String motivationLetter) {
        this.motivationLetter = motivationLetter;
    }

    public int getMaxDesiredSalary() {
        return maxDesiredSalary;
    }

    public void setMaxDesiredSalary(int maxDesiredSalary) {
        this.maxDesiredSalary = maxDesiredSalary;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public JobApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(JobApplicationStatus status) {
        this.status = status;
    }

    public Professional getProfessional() {
        return professional;
    }

    public void setProfessional(Professional professional) {
        this.professional = professional;
    }

    public Set<Skill> getSkills() {
        return skills;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }

    public Set<JobAd> getListOfAdMatchRequests() {
        return listOfAdMatchRequests;
    }

    public void setListOfAdMatchRequests(Set<JobAd> listOfAdMatchRequests) {
        this.listOfAdMatchRequests = listOfAdMatchRequests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobApplication that = (JobApplication) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
