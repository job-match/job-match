package com.project.jobmatch.repositories.interfaces;

import com.project.jobmatch.models.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Integer> {

    List<JobApplication> findJobApplicationsByProfessionalId(int id);
}
