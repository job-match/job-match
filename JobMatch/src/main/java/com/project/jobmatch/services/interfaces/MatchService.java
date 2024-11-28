package com.project.jobmatch.services.interfaces;

import com.project.jobmatch.models.Company;
import com.project.jobmatch.models.JobAd;
import com.project.jobmatch.models.JobApplication;
import com.project.jobmatch.models.Professional;

public interface MatchService {

    void confirmMatchWithJobApplication(JobAd jobAd, JobApplication jobApplication, Company companyAuthenticated);

    void confirmMatchWithJobAd(JobAd jobAd, JobApplication jobApplication, Professional professionalAuthenticated);

    void createMatch(JobAd jobAd, JobApplication jobApplication);

}
