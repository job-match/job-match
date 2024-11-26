package com.project.jobmatch.repositories.interfaces;

import com.project.jobmatch.models.JobAd;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface JobAdRepository extends JpaRepository<JobAd, Integer> {
    Optional<JobAd> findJobAdById(int id);

    @Override
    List<JobAd> findAll();

}
