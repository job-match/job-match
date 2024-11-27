package com.project.jobmatch.services;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.EntityDuplicateException;
import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.models.Company;
import com.project.jobmatch.models.JobAd;
import com.project.jobmatch.repositories.interfaces.JobAdRepository;
import com.project.jobmatch.services.interfaces.JobAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobAdServiceImpl implements JobAdService {

    private static final String MODIFY_JOB_AD_ERROR_MESSAGE = "Only job ad owner can make changes to the job ad info.";


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

    @Override
    public void createJobAd(JobAd jobAd, Company company) {
        boolean duplicateExists = true;
        try {
            getJobAdByTitle(jobAd.getPositionTitle());
        } catch (EntityNotFoundException e) {
            duplicateExists = false;
        }

        if (duplicateExists) {
            throw new EntityDuplicateException("Job Ad", "title", jobAd.getPositionTitle());
        } else {
            jobAdRepository.save(jobAd);
        }
    }

    @Override
    public void deleteJobAd(int id, Company company) {
        JobAd jobAd = getJobAdById(id);
        checkModifyPermissions(company, jobAd);
        jobAdRepository.delete(jobAd);

    }

    @Override
    public JobAd getJobAdByTitle(String title) {
        return jobAdRepository
                .findJobAdByPositionTitle(title)
                .orElseThrow(() -> new EntityNotFoundException("Job Ad", "title", title));
    }

    @Override
    public void updateJobAd(JobAd jobAd, Company company) {
        checkModifyPermissions(company, jobAd);
        jobAdRepository.save(jobAd);
    }

    private void checkModifyPermissions(Company company, JobAd jobAd) {
        if(!(jobAd.getCompany().equals(company))) {
            throw new AuthorizationException(MODIFY_JOB_AD_ERROR_MESSAGE);
        }
    }
}
