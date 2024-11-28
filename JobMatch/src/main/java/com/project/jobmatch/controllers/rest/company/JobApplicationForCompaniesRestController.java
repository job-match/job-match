package com.project.jobmatch.controllers.rest.company;

import com.project.jobmatch.helpers.AuthenticationHelper;
import com.project.jobmatch.helpers.ModelMapper;
import com.project.jobmatch.services.interfaces.JobAdService;
import com.project.jobmatch.services.interfaces.JobApplicationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
