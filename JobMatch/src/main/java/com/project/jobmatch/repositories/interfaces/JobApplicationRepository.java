package com.project.jobmatch.repositories.interfaces;

import com.project.jobmatch.models.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Integer> {
//    Optional<JobApplication> findJobApplicationByTitle(String title);
}
