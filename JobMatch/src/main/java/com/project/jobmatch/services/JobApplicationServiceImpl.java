package com.project.jobmatch.services;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.models.Company;
import com.project.jobmatch.models.JobApplication;
import com.project.jobmatch.models.Professional;
import com.project.jobmatch.repositories.interfaces.JobApplicationRepository;
import com.project.jobmatch.services.interfaces.JobApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {

    private static final String MODIFY_PROFILE_ERROR_MESSAGE =
            "Only professional account's owner can see their job applications!";

    private final JobApplicationRepository jobApplicationRepository;

    @Autowired
    public JobApplicationServiceImpl(JobApplicationRepository jobApplicationRepository) {
        this.jobApplicationRepository = jobApplicationRepository;
    }

    private void checkModifyPermissions
            (Professional professionalAuthenticated, Professional professionalToRetrieveJobAppsFrom) {
        if (!(professionalAuthenticated.equals(professionalToRetrieveJobAppsFrom))) {
            throw new AuthorizationException(MODIFY_PROFILE_ERROR_MESSAGE);
        }
    }

    @Override
    public List<JobApplication> getAllJobApplicationsOfProfessional(Professional professionalToRetrieveJobAppsFrom, Professional professionalAuthenticated) {
        checkModifyPermissions(professionalAuthenticated, professionalToRetrieveJobAppsFrom);
        return jobApplicationRepository.findJobApplicationsByProfessionalId(professionalToRetrieveJobAppsFrom.getId());
    }

}
