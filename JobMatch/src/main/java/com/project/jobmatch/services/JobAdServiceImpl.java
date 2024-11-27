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

@Service
public class JobAdServiceImpl implements JobAdService {

    private static final String MODIFY_JOB_AD_ERROR_MESSAGE = "Only job ad owner can make changes to the job ad info.";
    public static final String YOU_ALREADY_APPLIED_ERROR_MESSAGE = "You already applied for this job ad!";
    public static final String APPLICATION_DENIED_ERROR_MESSAGE = "You do not meet ad's requirements!";


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

    private boolean checkLocationMatch(String jobAdLocationName, String jobApplicationLocationName) {

        if (jobAdLocationName.equalsIgnoreCase("Remote")) {
            return true;

        } else if (jobAdLocationName.equalsIgnoreCase("Hybrid") &&
                (!jobApplicationLocationName.equalsIgnoreCase("Remote"))) {
            return true;

        } else {
            return jobAdLocationName.equalsIgnoreCase(jobApplicationLocationName);
        }
    }


    @Override
    public JobAd getJobAdByTitle(String title) {
        return jobAdRepository
                .findJobAdByPositionTitle(title)
                .orElseThrow(() -> new EntityNotFoundException("Job Ad", "title", title));
    }

    @Override
    public void updateJobAd(JobAd jobAd, Company company) {
        checkModifyPermissions(company, jobAd);
        jobAdRepository.save(jobAd);
    }

    private void checkModifyPermissions(Company company, JobAd jobAd) {
        if (!(jobAd.getCompany().equals(company))) {
            throw new AuthorizationException(MODIFY_JOB_AD_ERROR_MESSAGE);
        }
    }

    private boolean checkSalaryMatch(double minJobAdSalary,
                                     double maxJobAdSalary,
                                     double minJobAppSalary,
                                     double maxJobAppSalary) {

        return (minJobAdSalary * 0.8 <= minJobAppSalary && minJobAppSalary <= maxJobAdSalary * 1.2) &&
                (minJobAdSalary * 0.8 <= maxJobAppSalary && maxJobAppSalary <= maxJobAdSalary * 1.2);
    }

    private boolean checkSkillsAndRequirements(Set<Skill> skills, Set<Requirement> requirements) {
        int requirementsMetCounter = 0;
        double thresholdRequirements = 50.0;

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

        return thresholdRequirements <= metRequirementsPercentage;
    }

}
