package com.project.jobmatch.controllers.rest.professional;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.helpers.AuthenticationHelper;
import com.project.jobmatch.helpers.ModelMapper;
import com.project.jobmatch.models.JobApplication;
import com.project.jobmatch.models.Professional;
import com.project.jobmatch.models.dto.JobApplicationDtoOut;
import com.project.jobmatch.services.interfaces.JobApplicationService;
import com.project.jobmatch.services.interfaces.ProfessionalService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/professional-portal/job-applications")
public class JobApplicationForProfessionalsRestController {

    private final AuthenticationHelper authenticationHelper;
    private final JobApplicationService jobApplicationService;
    private final ProfessionalService professionalService;
    private final ModelMapper modelMapper;

    public JobApplicationForProfessionalsRestController(AuthenticationHelper authenticationHelper, JobApplicationService jobApplicationService, ProfessionalService professionalService, ModelMapper modelMapper) {
        this.authenticationHelper = authenticationHelper;
        this.jobApplicationService = jobApplicationService;
        this.professionalService = professionalService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/professional/{id}")
    public List<JobApplicationDtoOut> getAllJobApplicationsOfProfessional(@RequestHeader HttpHeaders httpHeaders,
                                                                          @PathVariable int id) {
        try {
            Professional professionalAuthenticated = authenticationHelper.tryGetProfessional(httpHeaders);
            Professional professionalToRetrieveJobAppsFrom = professionalService.getProfessionalById(id);
            List<JobApplication> jobApplications =
                    jobApplicationService.getAllJobApplicationsOfProfessional
                            (professionalToRetrieveJobAppsFrom, professionalAuthenticated);
            return modelMapper.fromSetJobApplicationToSetJobApplicationDtoOut(jobApplications);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
