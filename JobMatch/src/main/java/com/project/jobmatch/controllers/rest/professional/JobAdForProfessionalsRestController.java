package com.project.jobmatch.controllers.rest.professional;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.EntityDuplicateException;
import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.exceptions.MatchRequestDeniedException;
import com.project.jobmatch.helpers.AuthenticationHelper;
import com.project.jobmatch.helpers.ModelMapper;
import com.project.jobmatch.models.JobAd;
import com.project.jobmatch.models.JobApplication;
import com.project.jobmatch.models.dto.JobAdDtoOut;
import com.project.jobmatch.services.interfaces.JobAdService;
import com.project.jobmatch.services.interfaces.JobApplicationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/professional-portal/job-ads")
public class JobAdForProfessionalsRestController {

    private final JobAdService jobAdService;
    private final AuthenticationHelper authenticationHelper;
    private final ModelMapper modelMapper;
    private final JobApplicationService jobApplicationService;

    public JobAdForProfessionalsRestController(JobAdService jobAdService,
                                               AuthenticationHelper authenticationHelper,
                                               ModelMapper modelMapper,
                                               JobApplicationService jobApplicationService) {
        this.jobAdService = jobAdService;
        this.authenticationHelper = authenticationHelper;
        this.modelMapper = modelMapper;
        this.jobApplicationService = jobApplicationService;
    }

    @GetMapping
    public List<JobAdDtoOut> getAllJobAds(@RequestHeader HttpHeaders headers) {
        try{
            authenticationHelper.tryGetProfessional(headers);
            List<JobAd> jobAdList = jobAdService.getAll();

            return modelMapper.fromJobAdToJobAdDtoOutList(jobAdList);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @GetMapping("/search")
    public List<JobAdDtoOut> searchJobAds(@RequestHeader HttpHeaders headers,
                                          @RequestParam(required = false) String positionTitle,
                                          @RequestParam(required = false) String location,
                                          @RequestParam(required = false) Double minSalary,
                                          @RequestParam(required = false) Double maxSalary,
                                          @RequestParam(required = false) String requirement) {
        try {
            authenticationHelper.tryGetProfessional(headers);

            List<JobAd> jobAds = jobAdService.searchJobAds(positionTitle, location, minSalary, maxSalary, requirement);

            return modelMapper.fromJobAdToJobAdDtoOutList(jobAds);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public JobAdDtoOut getJobAdById(@RequestHeader HttpHeaders headers,
                                    @PathVariable int id) {
        try {
            authenticationHelper.tryGetProfessional(headers);

            return modelMapper.fromJobAdToJobAdDtoOut(jobAdService.getJobAdById(id));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    // /api/professional-portal/job-ads/{jobAdId}/match-request-by/{jobAppId}
    // /api/professional-portal/job-ads/1/match-request-by/12
    @PostMapping("/{jobAdId}/match-request-by/{jobAppId}")
    public void jobApplicationRequestMatchWithJobAd(@RequestHeader HttpHeaders headers,
                                                    @PathVariable int jobAdId,
                                                    @PathVariable int jobAppId) {
        try{
            authenticationHelper.tryGetProfessional(headers);

            JobAd jobAd = jobAdService.getJobAdById(jobAdId);
            JobApplication jobApplication = jobApplicationService.getJobApplicationById(jobAppId);

            jobAdService.addJobApplicationToListOfApplicationMatchRequests(jobAd, jobApplication);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (MatchRequestDeniedException e) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, e.getMessage());
        }
    }
}
