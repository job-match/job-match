package com.project.jobmatch.repositories.interfaces;

import com.project.jobmatch.models.JobAd;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JobAdRepository extends JpaRepository<JobAd, Integer> {
    JobAd findJobAdById(int id);

    @Override
    List<JobAd> findAll();

}
