package com.project.jobmatch.services;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.models.Company;
import com.project.jobmatch.models.JobAd;
import com.project.jobmatch.models.JobApplication;
import com.project.jobmatch.models.Match;
import com.project.jobmatch.repositories.interfaces.JobAdRepository;
import com.project.jobmatch.repositories.interfaces.MatchRepository;
import com.project.jobmatch.services.interfaces.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchServiceImpl implements MatchService {

    public static final String JOB_AD_OWNER_ERROR_MESSAGE = "Only company which is owner of this Job Ad can confirm match with Job Application!";
    private final MatchRepository matchRepository;
    private final JobAdRepository jobAdRepository;

    @Autowired
    public MatchServiceImpl(MatchRepository matchRepository,
                            JobAdRepository jobAdRepository) {
        this.matchRepository = matchRepository;
        this.jobAdRepository = jobAdRepository;
    }

    @Override
    public void createMatch(JobAd jobAd, JobApplication jobApplication, Company companyAuthenticated) {

        checkPermissions(companyAuthenticated, jobAd.getCompany());

        Match match = new Match();
        match.setJobAd(jobAd);
        match.setJobApplication(jobApplication);

        matchRepository.save(match);

        jobAd.getListOfApplicationMatchRequests().remove(jobApplication);
        jobAdRepository.save(jobAd);
    }

    private void checkPermissions(Company companyAuthenticated, Company company) {
        if (!(companyAuthenticated.equals(company))) {
            throw new AuthorizationException(JOB_AD_OWNER_ERROR_MESSAGE);
        }
    }
}
