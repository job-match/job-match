package com.project.jobmatch.services.interfaces;

import com.project.jobmatch.models.Company;
import com.project.jobmatch.models.JobAd;
import com.project.jobmatch.models.JobApplication;

public interface MatchService {

    void createMatch(JobAd jobAd, JobApplication jobApplication, Company companyAuthenticated);
}
