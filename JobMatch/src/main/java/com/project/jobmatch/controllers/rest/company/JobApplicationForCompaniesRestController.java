package com.project.jobmatch.controllers.rest.company;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.MatchRequestDeniedException;
import com.project.jobmatch.exceptions.MatchRequestDuplicateException;
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

import javax.naming.AuthenticationException;
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
    public List<JobApplicationDtoOut> getAllJobApplications(@RequestHeader HttpHeaders headers) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/search")
    public List<JobApplicationDtoOut> searchJobApplications(@RequestHeader HttpHeaders headers) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/{id}")
    public JobApplicationDtoOut getJobApplicationById(@RequestHeader HttpHeaders headers,
                                                      @PathVariable int id) {
        throw new UnsupportedOperationException();
    }

    // /api/company-portal/job-applications/{jobApplicationId}/match-request-by/{jobAdId}
    // /api/company-portal/job-applications/{12}/match-request-by/{1}
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