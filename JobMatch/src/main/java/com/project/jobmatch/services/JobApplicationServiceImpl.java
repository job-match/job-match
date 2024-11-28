package com.project.jobmatch.services;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.EntityDuplicateException;
import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.exceptions.MatchRequestDeniedException;
import com.project.jobmatch.models.*;
import com.project.jobmatch.repositories.interfaces.JobApplicationRepository;
import com.project.jobmatch.services.interfaces.JobApplicationService;
import com.project.jobmatch.services.interfaces.MatchService;
import com.project.jobmatch.services.interfaces.ProfessionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import static com.project.jobmatch.helpers.ServicesConstants.*;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final ProfessionalService professionalService;
    private final MatchService matchService;

    @Autowired
    public JobApplicationServiceImpl(JobApplicationRepository jobApplicationRepository,
                                     ProfessionalService professionalService,
                                     MatchService matchService) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.professionalService = professionalService;
        this.matchService = matchService;
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
    public List<JobApplication> getAllJobApplications(String status) {
        return jobApplicationRepository.findJobApplicationsByStatus(status);
    }

    @Override
    public List<JobApplication> searchJobApplications(String location,
                                                      Double minSalary,
                                                      Double maxSalary,
                                                      String skill,
                                                      String keyword) {
        return jobApplicationRepository.searchJobApplications(location, minSalary, maxSalary, skill, keyword);
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

        for (JobAd ad : jobApplicationToDelete.getListOfAdMatchRequests()) {
            ad.getListOfApplicationMatchRequests().remove(jobApplicationToDelete);
        }
        jobApplicationToDelete.getListOfAdMatchRequests().clear();

        Professional professional = jobApplicationToDelete.getProfessional();
        if (professional != null) {
            professional.getJobApplications().remove(jobApplicationToDelete);
        }

        jobApplicationRepository.delete(jobApplicationToDelete);
    }

    @Override
    public void addJobAdToListOfAdMatchRequests(JobApplication jobApplication, JobAd jobAd) {
        if (jobAd.getListOfApplicationMatchRequests().contains(jobApplication)) {
            matchService.createMatch(jobAd, jobApplication);

        } else {
            boolean doSalariesMatch = checkSalaryMatch(jobApplication.getMinDesiredSalary(),
                    jobApplication.getMaxDesiredSalary(),
                    jobAd.getMinSalaryBoundary(),
                    jobAd.getMaxSalaryBoundary());

            boolean doSkillsAndRequirementsMatch = checkSkillsAndRequirements(jobApplication.getSkills(),
                    jobAd.getRequirements());

            boolean doLocationsMatch = checkLocations(jobApplication.getLocation().getName(),
                    jobAd.getLocation().getName());

            if (doSalariesMatch && doSkillsAndRequirementsMatch && doLocationsMatch) {
                if (jobApplication.getListOfAdMatchRequests().contains(jobAd)) {
                    throw new EntityDuplicateException(YOU_ALREADY_SHOWED_INTEREST_ERROR_MESSAGE);
                }
                jobApplication.getListOfAdMatchRequests().add(jobAd);
                jobApplicationRepository.save(jobApplication);
            } else {
                throw new MatchRequestDeniedException(AD_REQUEST_DENIED_ERROR_MESSAGE);
            }
        }
    }

    private boolean checkSalaryMatch(double minJobAppSalary,
                                     double maxJobAppSalary,
                                     double minJobAdSalary,
                                     double maxJobAdSalary) {
        return (minJobAdSalary * MIN_SALARY_THRESHOLD_COEFFICIENT <= minJobAppSalary
                && minJobAppSalary <= maxJobAdSalary * MAX_SALARY_THRESHOLD_COEFFICIENT) &&
                (minJobAdSalary * MIN_SALARY_THRESHOLD_COEFFICIENT <= maxJobAppSalary
                        && maxJobAppSalary <= maxJobAdSalary * MAX_SALARY_THRESHOLD_COEFFICIENT);
    }

    private boolean checkSkillsAndRequirements(Set<Skill> skills, Set<Requirement> requirements) {
        int requirementsMetCounter = 0;

        for (Requirement requirement : requirements) {
            String requirementType = requirement.getType();

            for (Skill skill : skills) {
                String skillType = skill.getType();
                if (skillType.equalsIgnoreCase(requirementType)) {
                    requirementsMetCounter++;
                }
            }
        }

        double metRequirementsPercentage = (requirementsMetCounter * 1.0 / requirements.size()) * 100;

        return REQUIREMENTS_THRESHOLD_PERCENTAGE <= metRequirementsPercentage;
    }

    private boolean checkLocations(String jobApplicationLocationName, String jobAdLocationName) {
        if (jobAdLocationName.equalsIgnoreCase(REMOTE_LOCATION)) {
            return true;

        } else if (jobAdLocationName.equalsIgnoreCase(HYBRID_LOCATION) &&
                (!jobApplicationLocationName.equalsIgnoreCase(REMOTE_LOCATION))) {
            return true;

        } else {
            return jobAdLocationName.equalsIgnoreCase(jobApplicationLocationName);
        }
    }
}