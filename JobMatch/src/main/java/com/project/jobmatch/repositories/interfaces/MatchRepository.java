package com.project.jobmatch.repositories.interfaces;

import com.project.jobmatch.models.JobAd;
import com.project.jobmatch.models.JobApplication;
import com.project.jobmatch.models.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, Integer> {

    boolean existsMatchByJobAdAndJobApplication(JobAd jobAd, JobApplication jobApplication);
}
