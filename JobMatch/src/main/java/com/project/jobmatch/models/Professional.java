package com.project.jobmatch.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.jobmatch.models.enums.ProfessionalStatus;
import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "professionals")
public class Professional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "professional_id")
    private int id;

    @Column(name = "username")
    private String username;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "summary")
    private String summary;

    @OneToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "status")
    private ProfessionalStatus status;

    @OneToOne
    @JoinColumn(name = "picture_id")
    private Picture picture_id;

    @OneToMany(mappedBy = "professional", fetch = FetchType.EAGER)
    private Set<JobApplication> jobApplications;

    public Professional() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ProfessionalStatus getStatus() {
        return status;
    }

    public void setStatus(ProfessionalStatus status) {
        this.status = status;
    }

    public Picture getPicture_id() {
        return picture_id;
    }

    public void setPicture_id(Picture picture_id) {
        this.picture_id = picture_id;
    }

    public Set<JobApplication> getJobApplications() {
        return jobApplications;
    }

    public void setJobApplications(Set<JobApplication> jobApplications) {
        this.jobApplications = jobApplications;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Professional that = (Professional) object;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
