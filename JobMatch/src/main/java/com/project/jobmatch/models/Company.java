package com.project.jobmatch.models;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private int id;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "contacts")
    private String contacts;

    @OneToOne
    @JoinColumn(name = "picture_id")
    private Picture picture;

    @OneToMany(mappedBy = "company", fetch = FetchType.EAGER)
    private Set<JobAd> jobAds;

    public Company() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getContacts() {
        return contacts;
    }

    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public Set<JobAd> getJobAds() {
        return jobAds;
    }

    public void setJobAds(Set<JobAd> jobAds) {
        this.jobAds = jobAds;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Company company = (Company) object;
        return id == company.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
