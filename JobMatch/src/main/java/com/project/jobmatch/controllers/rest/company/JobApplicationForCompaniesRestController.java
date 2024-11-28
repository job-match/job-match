package com.project.jobmatch.controllers.rest.company;

import com.project.jobmatch.helpers.AuthenticationHelper;
import com.project.jobmatch.helpers.ModelMapper;
import com.project.jobmatch.models.dto.JobApplicationDtoOut;
import com.project.jobmatch.services.interfaces.JobAdService;
import com.project.jobmatch.services.interfaces.JobApplicationService;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpHeaders;
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

}
