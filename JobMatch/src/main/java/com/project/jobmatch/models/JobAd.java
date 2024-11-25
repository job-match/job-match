package com.project.jobmatch.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.jobmatch.models.enums.JobAdStatus;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "job_ads")
public class JobAd {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_ad_id")
    private int id;

    @Column(name = "position_title")
    private String positionTitle;

    @Column(name = "min_salary_boundary")
    private double minSalaryBoundary;

    @Column(name = "max_salary_boundary")
    private double maxSalaryBoundary;

    @Column(name = "job_description")
    private String jobDescription;

    @Column(name = "status")
    private JobAdStatus status;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "location_id")
    private Location location;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "company_id")
    private Company company;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "job_ads_requirements",
            joinColumns = @JoinColumn(name = "job_ad_id"),
            inverseJoinColumns = @JoinColumn(name = "requirement_id")
    )
    private Set<Requirement> requirements;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "job_ads_job_applications",
            joinColumns = @JoinColumn(name = "job_ad_id"),
            inverseJoinColumns = @JoinColumn(name = "job_application_id")
    )
    private Set<JobApplication> listOfApplicationMatchRequests;

    public JobAd() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPositionTitle() {
        return positionTitle;
    }

    public void setPositionTitle(String positionTitle) {
        this.positionTitle = positionTitle;
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

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public JobAdStatus getStatus() {
        return status;
    }

    public void setStatus(JobAdStatus status) {
        this.status = status;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Set<Requirement> getRequirements() {
        return requirements;
    }

    public void setRequirements(Set<Requirement> requirements) {
        this.requirements = requirements;
    }

    public Set<JobApplication> getListOfApplicationMatchRequests() {
        return listOfApplicationMatchRequests;
    }

    public void setListOfApplicationMatchRequests(Set<JobApplication> listOfApplicationMatchRequests) {
        this.listOfApplicationMatchRequests = listOfApplicationMatchRequests;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobAd jobAd = (JobAd) o;
        return id == jobAd.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
