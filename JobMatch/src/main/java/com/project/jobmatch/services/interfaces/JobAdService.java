package com.project.jobmatch.services.interfaces;

import com.project.jobmatch.models.Company;
import com.project.jobmatch.models.JobAd;

import java.util.List;

public interface JobAdService {
    List<JobAd> getAll();
    JobAd getJobAdById(int id);
    JobAd createJobAd(JobAd jobAd, Company company);
}
