package com.project.jobmatch.services;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.models.*;
import com.project.jobmatch.repositories.interfaces.JobAdRepository;
import com.project.jobmatch.repositories.interfaces.JobApplicationRepository;
import com.project.jobmatch.repositories.interfaces.MatchRepository;
import com.project.jobmatch.services.interfaces.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchServiceImpl implements MatchService {

    public static final String JOB_AD_OWNER_ERROR_MESSAGE = "Only company which is owner of this Job Ad can confirm match with Job Application!";
    public static final String JOB_APPLICATION_OWNER_ERROR_MESSAGE = "Only professional which is owner of this Job Application can confirm match with Job Ad!";
    private final MatchRepository matchRepository;
    private final JobAdRepository jobAdRepository;
    private final JobApplicationRepository jobApplicationRepository;

    @Autowired
    public MatchServiceImpl(MatchRepository matchRepository,
                            JobAdRepository jobAdRepository,
                            JobApplicationRepository jobApplicationRepository) {
        this.matchRepository = matchRepository;
        this.jobAdRepository = jobAdRepository;
        this.jobApplicationRepository = jobApplicationRepository;
    }

    @Override
    public void confirmMatchWithJobApplication(JobAd jobAd,
                                               JobApplication jobApplication,
                                               Company companyAuthenticated) {

        if (!(companyAuthenticated.equals(jobAd.getCompany()))) {
            throw new AuthorizationException(JOB_APPLICATION_OWNER_ERROR_MESSAGE);
        }

        Match match = new Match();
        match.setJobAd(jobAd);
        match.setJobApplication(jobApplication);

        matchRepository.save(match);

        jobAd.getListOfApplicationMatchRequests().remove(jobApplication);
        jobAdRepository.save(jobAd);
    }

    @Override
    public void confirmMatchWithJobAd(JobAd jobAd,
                                      JobApplication jobApplication,
                                      Professional professionalAuthenticated) {

        if (!(professionalAuthenticated.equals(jobApplication.getProfessional()))) {
            throw new AuthorizationException(JOB_APPLICATION_OWNER_ERROR_MESSAGE);
        }

        Match match = new Match();
        match.setJobAd(jobAd);
        match.setJobApplication(jobApplication);

        matchRepository.save(match);

        jobApplication.getListOfAdMatchRequests().remove(jobAd);
        jobApplicationRepository.save(jobApplication);
    }
}
