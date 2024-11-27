package com.project.jobmatch.controllers.rest.professional;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.helpers.AuthenticationHelper;
import com.project.jobmatch.helpers.ModelMapper;
import com.project.jobmatch.models.JobAd;
import com.project.jobmatch.models.dto.JobAdDtoOut;
import com.project.jobmatch.services.interfaces.JobAdService;
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

    public JobAdForProfessionalsRestController(JobAdService jobAdService,
                                               AuthenticationHelper authenticationHelper,
                                               ModelMapper modelMapper) {
        this.jobAdService = jobAdService;
        this.authenticationHelper = authenticationHelper;
        this.modelMapper = modelMapper;
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


}
