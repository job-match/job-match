package com.project.jobmatch.controllers.rest.company;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.helpers.AuthenticationHelper;
import com.project.jobmatch.models.JobAd;
import com.project.jobmatch.services.interfaces.CompanyService;
import com.project.jobmatch.services.interfaces.JobAdService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/company-portal/job-ads")
public class JobAdForCompaniesRestController {

    private final JobAdService jobAdService;
    private final CompanyService companyService;
    private final AuthenticationHelper authenticationHelper;

    public JobAdForCompaniesRestController(JobAdService jobAdService, CompanyService companyService, AuthenticationHelper authenticationHelper) {
        this.jobAdService = jobAdService;
        this.companyService = companyService;
        this.authenticationHelper = authenticationHelper;
    }


    @GetMapping
    public List<JobAd> getAllJobAds(@RequestHeader HttpHeaders headers) {
        try{
            authenticationHelper.tryGetCompany(headers);
            List<JobAd> jobAdList = jobAdService.getAll();

            return jobAdList;
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public JobAd getJobAdById(@RequestHeader HttpHeaders headers,
                              @PathVariable int id) {
        try {
            authenticationHelper.tryGetCompany(headers);

            return jobAdService.getJobAdById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
