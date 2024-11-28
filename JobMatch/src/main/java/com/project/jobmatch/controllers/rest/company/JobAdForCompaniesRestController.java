package com.project.jobmatch.controllers.rest.company;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.EntityDuplicateException;
import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.helpers.AuthenticationHelper;
import com.project.jobmatch.helpers.ModelMapper;
import com.project.jobmatch.models.Company;
import com.project.jobmatch.models.JobAd;
import com.project.jobmatch.models.JobApplication;
import com.project.jobmatch.models.dto.JobAdDtoInUpdate;
import com.project.jobmatch.models.dto.JobAdDtoInCreate;
import com.project.jobmatch.models.dto.JobAdDtoOut;
import com.project.jobmatch.services.interfaces.CompanyService;
import com.project.jobmatch.services.interfaces.JobAdService;
import com.project.jobmatch.services.interfaces.JobApplicationService;
import com.project.jobmatch.services.interfaces.MatchService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/company-portal/job-ads")
public class JobAdForCompaniesRestController {

    private final JobAdService jobAdService;
    private final MatchService matchService;
    private final JobApplicationService jobApplicationService;
    private final AuthenticationHelper authenticationHelper;
    private final ModelMapper modelMapper;

    public JobAdForCompaniesRestController(JobAdService jobAdService,
                                           MatchService matchService,
                                           JobApplicationService jobApplicationService,
                                           AuthenticationHelper authenticationHelper,
                                           ModelMapper modelMapper) {
        this.jobAdService = jobAdService;
        this.matchService = matchService;
        this.jobApplicationService = jobApplicationService;
        this.authenticationHelper = authenticationHelper;
        this.modelMapper = modelMapper;
    }


    @GetMapping
    public List<JobAdDtoOut> getAllJobAds(@RequestHeader HttpHeaders headers) {
        try {
            authenticationHelper.tryGetCompany(headers);
            List<JobAd> jobAdList = jobAdService.getAll();

            return modelMapper.fromJobAdToJobAdDtoOutList(jobAdList);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public JobAdDtoOut getJobAdById(@RequestHeader HttpHeaders headers,
                                    @PathVariable int id) {
        try {
            authenticationHelper.tryGetCompany(headers);

            return modelMapper.fromJobAdToJobAdDtoOut(jobAdService.getJobAdById(id));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PostMapping()
    public JobAdDtoOut createJobAd(@RequestHeader HttpHeaders headers,
                                   @Valid @RequestBody JobAdDtoInCreate jobAdDtoInCreate) {
        try {
            Company company = authenticationHelper.tryGetCompany(headers);
            JobAd jobAd = modelMapper.fromJobAdDtoIn(jobAdDtoInCreate, company);
            jobAdService.createJobAd(jobAd);

            return modelMapper.fromJobAdToJobAdDtoOut(jobAd);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PostMapping("/{jobAdId}/match-requests/{jobAppId}")
    public void confirmMatchWithJobApplication(@RequestHeader HttpHeaders headers,
                                        @PathVariable int jobAdId,
                                        @PathVariable int jobAppId) {
        try {
            Company companyAuthenticated = authenticationHelper.tryGetCompany(headers);
            JobAd jobAd = jobAdService.getJobAdById(jobAdId);
            JobApplication jobApplication = jobApplicationService.getJobApplicationById(jobAppId);

            matchService.createMatch(jobAd, jobApplication, companyAuthenticated);

        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public void updateJobAd(@RequestHeader HttpHeaders headers,
                            @PathVariable int id,
                            @Valid @RequestBody JobAdDtoInUpdate jobAdDtoInUpdate) {
        try {
            Company company = authenticationHelper.tryGetCompany(headers);
            JobAd jobAd = modelMapper.fromJodAdDtoIn(id, jobAdDtoInUpdate);
            jobAdService.updateJobAd(jobAd, company);

        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
