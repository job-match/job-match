package com.project.jobmatch.services.interfaces;

import com.project.jobmatch.models.*;

import java.util.List;

public interface MatchService {

    void confirmMatchWithJobApplication(JobAd jobAd, JobApplication jobApplication, Company companyAuthenticated);

    void confirmMatchWithJobAd(JobAd jobAd, JobApplication jobApplication, Professional professionalAuthenticated);

    void rejectMatchWithJobAd(JobAd jobAd, JobApplication jobApplication, Professional professionalAuthenticated);

    void createMatch(JobAd jobAd, JobApplication jobApplication);

    List<JobApplication> getMatchedJobApplications(Company company);

    List<JobAd> getMatchedJobAds(Professional professional);

    List<Match> getAllMatches();
}
