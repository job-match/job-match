package com.project.jobmatch.services.interfaces;

import com.project.jobmatch.models.JobAd;
import com.project.jobmatch.models.JobApplication;
import com.project.jobmatch.models.Professional;

import java.util.List;

public interface JobApplicationService {

    void createJobApplication(JobApplication jobApplication);

    void updateJobApplication(Professional professionalAuthenticated, JobApplication jobApplicationMapped);

    void deleteJobApplication(JobApplication jobApplicationToDelete, Professional professionalAuthenticated);

    void addJobAdToListOfAdMatchRequests(JobApplication jobApplication, JobAd jobAd);

    JobApplication getJobApplicationById(int jobApplicationId);

    JobApplication getJobApplicationById(int jobApplicationId, String statusToIgnore);

    List<JobApplication> getAllJobApplicationsOfProfessional
            (Professional professionalToRetrieveJobAppsFrom, Professional professionalAuthenticated);

    List<JobApplication> getAllJobApplications();

    List<JobApplication> getAllJobApplications(String status);

    List<JobApplication> searchJobApplications(String location, Double minSalary, Double maxSalary, String skill, String keyword);
}
