package com.project.jobmatch.repositories.interfaces;

import com.project.jobmatch.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Integer> {

    boolean existsMatchByJobAdAndJobApplication(JobAd jobAd, JobApplication jobApplication);

    @Query("SELECT m.jobApplication FROM Match m WHERE m.jobAd.company = :company")
    List<JobApplication> findJobApplicationsByCompany(@Param("company") Company company);

    @Query("SELECT m.jobAd FROM Match m WHERE m.jobApplication.professional = :professional")
    List<JobAd> findJobAdByProfessional(@Param("professional") Professional professional);

    List<Match> findAll();
}
