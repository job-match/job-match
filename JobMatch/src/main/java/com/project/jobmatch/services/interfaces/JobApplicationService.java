package com.project.jobmatch.services.interfaces;

import com.project.jobmatch.models.JobApplication;
import com.project.jobmatch.models.Professional;

import java.util.List;

public interface JobApplicationService {

    List<JobApplication> getAllJobApplicationsOfProfessional
            (Professional professionalToRetrieveJobAppsFrom, Professional professionalAuthenticated);

    void createJobApplication(JobApplication jobApplication);
}
