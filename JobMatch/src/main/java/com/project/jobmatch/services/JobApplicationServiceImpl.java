package com.project.jobmatch.services;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.models.JobAd;
import com.project.jobmatch.models.JobApplication;
import com.project.jobmatch.models.Professional;
import com.project.jobmatch.repositories.interfaces.JobApplicationRepository;
import com.project.jobmatch.services.interfaces.JobApplicationService;
import com.project.jobmatch.services.interfaces.ProfessionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {

    private static final String MODIFY_PROFILE_ERROR_MESSAGE =
            "Only professional account's owner can see/modify their job applications!";
    private static final String DELETE_PROFILE_ERROR_MESSAGE =
            "Only professional account's owner can delete their job applications!";

    private final JobApplicationRepository jobApplicationRepository;
    private final ProfessionalService professionalService;

    @Autowired
    public JobApplicationServiceImpl(JobApplicationRepository jobApplicationRepository, ProfessionalService professionalService) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.professionalService = professionalService;
    }

    private void checkModifyPermissions
            (Professional professionalAuthenticated, Professional professionalToRetrieveJobAppsFrom) {
        if (!(professionalAuthenticated.equals(professionalToRetrieveJobAppsFrom))) {
            throw new AuthorizationException(MODIFY_PROFILE_ERROR_MESSAGE);
        }
    }

    private void checkDeletePermissions
            (Professional professionalAuthenticated, JobApplication jobApplicationToDelete) {
        Professional professionalJobApplicationOwner =
                professionalService.getProfessionalByJobApplicationId(jobApplicationToDelete.getId());
        if (!(professionalAuthenticated.equals(professionalJobApplicationOwner))) {
            throw new AuthorizationException(DELETE_PROFILE_ERROR_MESSAGE);
        }
    }

    @Override
    public List<JobApplication> getAllJobApplicationsOfProfessional(Professional professionalToRetrieveJobAppsFrom, Professional professionalAuthenticated) {
        checkModifyPermissions(professionalAuthenticated, professionalToRetrieveJobAppsFrom);
        return jobApplicationRepository.findJobApplicationsByProfessionalId(professionalToRetrieveJobAppsFrom.getId());
    }

    @Override
    public List<JobApplication> getAllJobApplications() {
        return jobApplicationRepository.findAll();
    }

    @Override
    public void createJobApplication(JobApplication jobApplication) {
        jobApplicationRepository.save(jobApplication);
    }

    @Override
    public void updateJobApplication(Professional professionalAuthenticated, JobApplication jobApplicationMapped) {
        checkModifyPermissions(professionalAuthenticated, jobApplicationMapped.getProfessional());
        jobApplicationRepository.save(jobApplicationMapped);
    }

    @Override
    public JobApplication getJobApplicationById(int jobApplicationId) {
        return jobApplicationRepository
                .findJobApplicationById(jobApplicationId)
                .orElseThrow(()-> new EntityNotFoundException("Job application", jobApplicationId));
    }

    @Override
    public void deleteJobApplication(JobApplication jobApplicationToDelete,
                                     Professional professionalAuthenticated) {
        checkDeletePermissions(professionalAuthenticated, jobApplicationToDelete);

        // Remove references of JobApplication from Job Add
        for (JobAd ad : jobApplicationToDelete.getListOfAdMatchRequests()) {
            ad.getListOfApplicationMatchRequests().remove(jobApplicationToDelete);
        }
        jobApplicationToDelete.getListOfAdMatchRequests().clear();

        // Remove references of JobApplication from Professional
        Professional professional = jobApplicationToDelete.getProfessional();
        if (professional != null) {
            professional.getJobApplications().remove(jobApplicationToDelete);
        }
        jobApplicationToDelete.setProfessional(null);

        jobApplicationToDelete.getSkills().clear();
        jobApplicationToDelete.setLocation(null);
        jobApplicationToDelete.setStatus(null);

        jobApplicationRepository.delete(jobApplicationToDelete);
    }

}
