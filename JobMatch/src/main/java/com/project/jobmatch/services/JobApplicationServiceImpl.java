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

import static com.project.jobmatch.helpers.RestControllersConstants.JOB_APP_STATUS_TO_IGNORE;
import static com.project.jobmatch.helpers.ServicesConstants.*;

import static com.project.jobmatch.helpers.RestControllersConstants.JOB_APP_STATUS_TO_ACCEPT;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {

    private final JobApplicationRepository jobApplicationRepository;
    private final ProfessionalService professionalService;
    private final MailjetServiceImpl mailjetService;
    private final MatchService matchService;

    @Autowired
    public JobApplicationServiceImpl(JobApplicationRepository jobApplicationRepository,
                                     ProfessionalService professionalService, MailjetServiceImpl mailjetService,
                                     MatchService matchService) {
        this.jobApplicationRepository = jobApplicationRepository;
        this.professionalService = professionalService;
        this.mailjetService = mailjetService;
        this.matchService = matchService;
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
    public List<JobApplication> getAllActiveJobApplications() {
        return jobApplicationRepository.findJobApplicationsByStatus(JOB_APP_STATUS_TO_ACCEPT);
    }

    @Override
    public List<JobApplication> getAllActiveJobApplicationsOfProfessional(Professional professional) {

        return jobApplicationRepository.findActiveJobApplicationsOfProfessional(professional.getId(), 1);
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
    public void createJobApplication(JobApplication jobApplication, Professional professionalAuthenticated) {
        if (professionalAuthenticated.getStatus().getType().equalsIgnoreCase(PROFESSIONAL_STATUS_BUSY)){
            throw new AuthorizationException(CREATE_JOB_APPLICATION_ERROR_MESSAGE);
        }

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
    public JobApplication getJobApplicationByIdFromCompany(int id) {
        return jobApplicationRepository
                .findJobApplicationByIdFromCompany(id, JOB_APP_STATUS_TO_IGNORE)
                .orElseThrow(()-> new EntityNotFoundException("Job application", id));
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

            // Notifying both parties for a successful match.
            mailjetService.sendEmail(
                    jobAd.getCompany().getEmail(),
                    jobAd.getCompany().getName(),
                    SUCCESSFUL_MATCH_SUBJECT_MESSAGE,
                    SUCCESSFUL_MATCH_TEXT_CONTENT,
                    String.format(SUCCESSFUL_MATCH_HTML_CONTENT,
                            jobAd.getCompany().getName())
            );
            mailjetService.sendEmail(
                    jobApplication.getProfessional().getEmail(),
                    jobApplication.getProfessional().getFirstName(),
                    SUCCESSFUL_MATCH_SUBJECT_MESSAGE,
                    SUCCESSFUL_MATCH_TEXT_CONTENT,
                    String.format(SUCCESSFUL_MATCH_HTML_CONTENT,
                            jobApplication.getProfessional().getFirstName())
            );

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

                //Notifying the professional that a company, through its job ad,
                // has sent a match request for a job application of theirs.
                mailjetService.sendEmail(
                        jobApplication.getProfessional().getEmail(),
                        jobApplication.getProfessional().getFirstName(),
                        JOB_APP_MATCH_REQUEST_SUBJECT_MESSAGE,
                        String.format(JOB_APP_MATCH_REQUEST_TEXT_CONTENT,
                                jobApplication.getProfessional().getFirstName(),
                                jobAd.getCompany().getName()),
                        String.format(JOB_APP_MATCH_REQUEST_HTML_CONTENT,
                                jobApplication.getProfessional().getFirstName())
                );

            } else {
                throw new MatchRequestDeniedException(AD_REQUEST_DENIED_ERROR_MESSAGE);
            }
        }
    }

    public boolean checkSalaryMatch(double minJobAppSalary,
                                     double maxJobAppSalary,
                                     double minJobAdSalary,
                                     double maxJobAdSalary) {
        return (minJobAdSalary * MIN_SALARY_THRESHOLD_COEFFICIENT <= minJobAppSalary
                && minJobAppSalary <= maxJobAdSalary * MAX_SALARY_THRESHOLD_COEFFICIENT) &&
                (minJobAdSalary * MIN_SALARY_THRESHOLD_COEFFICIENT <= maxJobAppSalary
                        && maxJobAppSalary <= maxJobAdSalary * MAX_SALARY_THRESHOLD_COEFFICIENT);
    }

    public boolean checkSkillsAndRequirements(Set<Skill> skills, Set<Requirement> requirements) {
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

    public boolean checkLocations(String jobApplicationLocationName, String jobAdLocationName) {
        if (jobAdLocationName.equalsIgnoreCase(REMOTE_LOCATION)) {
            return true;

        } else if (jobAdLocationName.equalsIgnoreCase(HYBRID_LOCATION) &&
                (!jobApplicationLocationName.equalsIgnoreCase(REMOTE_LOCATION))) {
            return true;

        } else {
            return jobAdLocationName.equalsIgnoreCase(jobApplicationLocationName);
        }
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
}
