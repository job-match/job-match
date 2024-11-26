package com.project.jobmatch.services;

import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.models.JobAd;
import com.project.jobmatch.repositories.interfaces.JobAdRepository;
import com.project.jobmatch.services.interfaces.JobAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobAdServiceImpl implements JobAdService {

    private JobAdRepository jobAdRepository;

    @Autowired
    public JobAdServiceImpl(JobAdRepository jobAdRepository) {
        this.jobAdRepository = jobAdRepository;
    }


    @Override
    public List<JobAd> getAll() {
        return jobAdRepository.findAll();
    }

    @Override
    public JobAd getJobAdById(int id) {
        return jobAdRepository
                .findJobAdById(id)
                .orElseThrow(() -> new EntityNotFoundException("Job ad", id));
    }
}
