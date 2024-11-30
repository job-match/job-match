package com.project.jobmatch.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "pictures")
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "picture_id")
    private int id;

    @Column(name = "url")
    private String url;

    @Column(name = "public_id")
    private String publicId;

    public Picture() {
    }

    @Lob
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Lob
    public String getPublicId() {
        return publicId;
    }

    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Picture picture = (Picture) object;
        return id == picture.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
