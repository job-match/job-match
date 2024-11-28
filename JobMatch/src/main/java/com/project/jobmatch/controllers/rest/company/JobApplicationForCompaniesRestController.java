package com.project.jobmatch.controllers.rest.company;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.helpers.AuthenticationHelper;
import com.project.jobmatch.helpers.ModelMapper;
import com.project.jobmatch.models.JobApplication;
import com.project.jobmatch.models.dto.JobApplicationDtoOut;
import com.project.jobmatch.services.interfaces.JobAdService;
import com.project.jobmatch.services.interfaces.JobApplicationService;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.http.HttpHeaders;

import java.util.List;

@RestController
@RequestMapping("/api/company-portal/job-applications")
public class JobApplicationForCompaniesRestController {

    private final JobApplicationService jobApplicationService;
    private final JobAdService jobAdService;
    private final AuthenticationHelper authenticationHelper;
    private final ModelMapper modelMapper;

    public JobApplicationForCompaniesRestController(JobApplicationService jobApplicationService,
                                                    JobAdService jobAdService,
                                                    AuthenticationHelper authenticationHelper,
                                                    ModelMapper modelMapper) {
        this.jobApplicationService = jobApplicationService;
        this.jobAdService = jobAdService;
        this.authenticationHelper = authenticationHelper;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<JobApplicationDtoOut> getAllJobApplications(@RequestHeader HttpHeaders httpHeaders) {
        try {
            authenticationHelper.tryGetCompany(httpHeaders);
            List<JobApplication> jobApplicationList = jobApplicationService.getAllJobApplications();

            return modelMapper.fromListJobApplicationToListJobApplicationDtoOut(jobApplicationList);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @GetMapping("/search")
    public List<JobApplicationDtoOut> searchJobApplications(@RequestHeader HttpHeaders httpHeaders,
                                                            @RequestParam(required = false) String location,
                                                            @RequestParam(required = false) Double minSalary,
                                                            @RequestParam(required = false) Double maxSalary,
                                                            @RequestParam(required = false) String skill,
                                                            @RequestParam(required = false) String keyword) {
        try {
            authenticationHelper.tryGetCompany(httpHeaders);

            List<JobApplication> jobApplicationList = jobApplicationService.searchJobApplications(
                    location,
                    minSalary,
                    maxSalary,
                    skill,
                    keyword);

            return modelMapper.fromListJobApplicationToListJobApplicationDtoOut(jobApplicationList);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public JobApplicationDtoOut getJobApplicationById(@RequestHeader HttpHeaders httpHeaders,
                                                      @PathVariable int id) {
        try {
            authenticationHelper.tryGetCompany(httpHeaders);

            return modelMapper.fromJobApplicationToJobApplicationDtoOut(
                    jobApplicationService.getJobApplicationById(id));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    // /api/company-portal/job-applications/{jobApplicationId}/match-request-by/{jobAdId}
    @PostMapping("/{jobApplicationId}/match-request-by/{jobAdId}")
    public void jobAdRequestMatchWithJobApplication(@RequestHeader HttpHeaders headers,
                                                    @PathVariable int jobApplicationId,
                                                    @PathVariable int jobAdId) {
        throw new UnsupportedOperationException();
    }
}
