package com.project.jobmatch.services.interfaces;

import com.project.jobmatch.models.Company;
import com.project.jobmatch.models.JobAd;
import com.project.jobmatch.models.JobApplication;
import com.project.jobmatch.models.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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

    Page<JobAd> searchJobAdsPaginated(String positionTitle, String location, Double minSalary, Double maxSalary, String requirement, PageRequest pageRequest);

    List<JobAd> getJobAdsByLocation(Location location);

    List<JobAd> getSixMostRecentJobAds();

    List<JobAd> getJobAdsByCompanyId(int companyId);

    List<JobAd> getAllActiveJobAdsOfCompany(Company company);

    boolean checkIfOwnerOfJobAd(Company company, JobAd jobAd);
}