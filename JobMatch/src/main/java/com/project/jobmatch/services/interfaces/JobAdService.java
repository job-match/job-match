package com.project.jobmatch.services.interfaces;

import com.project.jobmatch.models.Company;
import com.project.jobmatch.models.JobAd;

import java.util.List;

public interface JobAdService {

    void updateJobAd(JobAd jobAd, Company company);

    void createJobAd(JobAd jobAd, Company company);

    JobAd getJobAdById(int id);

    JobAd getJobAdByTitle(String title);

    List<JobAd> getAll();

}