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
