package com.project.jobmatch.controllers.rest.company;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.MatchRequestDeniedException;
import com.project.jobmatch.exceptions.MatchRequestDuplicateException;
import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.helpers.AuthenticationHelper;
import com.project.jobmatch.helpers.ModelMapper;
import com.project.jobmatch.models.JobAd;
import com.project.jobmatch.models.JobApplication;
import com.project.jobmatch.models.dto.JobApplicationDtoOut;
import com.project.jobmatch.services.interfaces.JobAdService;
import com.project.jobmatch.services.interfaces.JobApplicationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

import static com.project.jobmatch.helpers.RestControllersConstants.JOB_APP_STATUS_TO_ACCEPT;
import static com.project.jobmatch.helpers.RestControllersConstants.JOB_APP_STATUS_TO_IGNORE;

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
            List<JobApplication> jobApplicationList = jobApplicationService.getAllJobApplications(JOB_APP_STATUS_TO_ACCEPT);

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
                    jobApplicationService.getJobApplicationById(id, JOB_APP_STATUS_TO_IGNORE));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    // /api/company-portal/job-applications/{jobApplicationId}/match-request-by/{jobAdId}
    // /api/company-portal/job-applications/12/match-request-by/1
    @PostMapping("/{jobApplicationId}/match-request-by/{jobAdId}")
    public void jobAdRequestMatchWithJobApplication(@RequestHeader HttpHeaders headers,
                                                    @PathVariable int jobApplicationId,
                                                    @PathVariable int jobAdId) {
        try {
            authenticationHelper.tryGetCompany(headers);

            JobApplication jobApplication = jobApplicationService.getJobApplicationById(jobApplicationId);
            JobAd jobAd = jobAdService.getJobAdById(jobAdId);

            jobApplicationService.addJobAdToListOfAdMatchRequests(jobApplication, jobAd);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (MatchRequestDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (MatchRequestDeniedException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
    }
}
