package com.project.jobmatch.services;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.exceptions.MatchRequestDeniedException;
import com.project.jobmatch.exceptions.MatchRequestDuplicateException;
import com.project.jobmatch.models.*;
import com.project.jobmatch.repositories.interfaces.JobAdRepository;
import com.project.jobmatch.services.interfaces.JobAdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;
import static com.project.jobmatch.services.ServicesConstants.*;

@Service
public class JobAdServiceImpl implements JobAdService {

    private JobAdRepository jobAdRepository;

    @Autowired
    public JobAdServiceImpl(JobAdRepository jobAdRepository) {
        this.jobAdRepository = jobAdRepository;
    }

    @Override
    public JobAd getJobAdById(int id) {
        return jobAdRepository
                .findJobAdById(id)
                .orElseThrow(() -> new EntityNotFoundException("Job ad", id));
    }

    @Override
    public List<JobAd> getAll() {
        return jobAdRepository.findAll();
    }

    @Override
    public List<JobAd> searchJobAds(String positionTitle,
                                    String location,
                                    Double minSalary,
                                    Double maxSalary,
                                    String requirement) {

        return jobAdRepository.searchJobAds(positionTitle, location, minSalary, maxSalary, requirement);
    }

    @Override
    public void createJobAd(JobAd jobAd) {
        jobAdRepository.save(jobAd);
    }

    @Override
    public void addJobApplicationToListOfApplicationMatchRequests(JobAd jobAd, JobApplication jobApplication) {
        boolean doSalariesMatch = checkSalaryMatch(jobAd.getMinSalaryBoundary(),
                jobAd.getMaxSalaryBoundary(),
                jobApplication.getMinDesiredSalary(),
                jobApplication.getMaxDesiredSalary());

        boolean doSkillsAndRequirementsMatch = checkSkillsAndRequirements(jobApplication.getSkills(),
                jobAd.getRequirements());

        boolean doLocationsMatch = checkLocationMatch(jobAd.getLocation().getName(), jobApplication.getLocation().getName());

        if (doSalariesMatch && doSkillsAndRequirementsMatch && doLocationsMatch) {
            if (jobAd.getListOfApplicationMatchRequests().contains(jobApplication)) {
                throw new MatchRequestDuplicateException(YOU_ALREADY_APPLIED_ERROR_MESSAGE);
            }
            jobAd.getListOfApplicationMatchRequests().add(jobApplication);
            jobAdRepository.save(jobAd);
        } else {
            throw new MatchRequestDeniedException(APPLICATION_DENIED_ERROR_MESSAGE);
        }
    }

    @Override
    public void deleteJobAd(JobAd jobAdToDelete, Company companyAuthenticated) {
        checkModifyPermissions(jobAdToDelete, companyAuthenticated);

        for (JobApplication jobApp : jobAdToDelete.getListOfApplicationMatchRequests()) {
            jobApp.getListOfAdMatchRequests().remove(jobAdToDelete);
        }
        jobAdToDelete.getListOfApplicationMatchRequests().clear();

        Company company = jobAdToDelete.getCompany();
        if (company != null) {
            company.getJobAds().remove(jobAdToDelete);
        }
        jobAdToDelete.setCompany(null);

        jobAdToDelete.getRequirements().clear();
        jobAdToDelete.setLocation(null);
        jobAdToDelete.setStatus(null);

        jobAdRepository.delete(jobAdToDelete);
    }


    @Override
    public JobAd getJobAdByTitle(String title) {
        return jobAdRepository
                .findJobAdByPositionTitle(title)
                .orElseThrow(() -> new EntityNotFoundException("Job Ad", "title", title));
    }

    @Override
    public void updateJobAd(JobAd jobAd, Company company) {
        checkModifyPermissions(jobAd, company);
        jobAdRepository.save(jobAd);
    }

    private void checkModifyPermissions(JobAd jobAd, Company company) {
        if (!(jobAd.getCompany().equals(company))) {
            throw new AuthorizationException(MODIFY_JOB_AD_ERROR_MESSAGE);
        }
    }

    private boolean checkSalaryMatch(double minJobAdSalary,
                                     double maxJobAdSalary,
                                     double minJobAppSalary,
                                     double maxJobAppSalary) {

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

    private boolean checkLocationMatch(String jobAdLocationName, String jobApplicationLocationName) {

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
