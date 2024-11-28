package com.project.jobmatch.services.interfaces;

import com.project.jobmatch.models.JobApplication;
import com.project.jobmatch.models.Professional;

import java.util.List;

public interface JobApplicationService {

    void createJobApplication(JobApplication jobApplication);

    void updateJobApplication(Professional professionalAuthenticated, JobApplication jobApplicationMapped);

    void deleteJobApplication(JobApplication jobApplicationToDelete, Professional professionalAuthenticated);

    JobApplication getJobApplicationById(int jobApplicationId);

    List<JobApplication> getAllJobApplicationsOfProfessional
            (Professional professionalToRetrieveJobAppsFrom, Professional professionalAuthenticated);
}
