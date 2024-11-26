package com.project.jobmatch.controllers.rest.company;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.helpers.AuthenticationHelper;
import com.project.jobmatch.helpers.ModelMapper;
import com.project.jobmatch.models.JobAd;
import com.project.jobmatch.models.dto.JobAdDtoOut;
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
    private final ModelMapper modelMapper;

    public JobAdForCompaniesRestController(JobAdService jobAdService, CompanyService companyService, AuthenticationHelper authenticationHelper, ModelMapper modelMapper) {
        this.jobAdService = jobAdService;
        this.companyService = companyService;
        this.authenticationHelper = authenticationHelper;
        this.modelMapper = modelMapper;
    }


    @GetMapping
    public List<JobAdDtoOut> getAllJobAds(@RequestHeader HttpHeaders headers) {
        try{
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
}
