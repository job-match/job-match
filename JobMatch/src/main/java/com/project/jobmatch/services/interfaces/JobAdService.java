package com.project.jobmatch.services.interfaces;

import com.project.jobmatch.models.Company;
import com.project.jobmatch.models.JobAd;
import com.project.jobmatch.models.JobApplication;
import com.project.jobmatch.models.Location;

import java.util.List;

public interface JobAdService {

    void updateJobAd(JobAd jobAd, Company company);

    void createJobAd(JobAd jobAd);

    void addJobApplicationToListOfApplicationMatchRequests(JobAd jobAd, JobApplication jobApplication);

    void deleteJobAd(JobAd jobAdToDelete, Company companyAuthenticated);

    JobAd getJobAdById(int id);

    JobAd getJobAdByTitle(String title);

    List<JobAd> getAll();

    List<JobAd> searchJobAds(String positionTitle, String location, Double minSalary, Double maxSalary, String requirement);

    List<JobAd> getJobAdsByLocation(Location location);

    List<JobAd> getSixMostRecentJobAds();
}