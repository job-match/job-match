package com.project.jobmatch.controllers.rest.professional;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.helpers.AuthenticationHelper;
import com.project.jobmatch.helpers.ModelMapper;
import com.project.jobmatch.models.JobApplication;
import com.project.jobmatch.models.Professional;
import com.project.jobmatch.models.dto.JobApplicationDtoInCreate;
import com.project.jobmatch.models.dto.JobApplicationDtoInUpdate;
import com.project.jobmatch.models.dto.JobApplicationDtoOut;
import com.project.jobmatch.services.interfaces.JobApplicationService;
import com.project.jobmatch.services.interfaces.ProfessionalService;
import jakarta.validation.Valid;
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

    @PostMapping()
    public JobApplicationDtoOut createJobApplication(@RequestHeader HttpHeaders httpHeaders,
                                               @Valid @RequestBody JobApplicationDtoInCreate jobApplicationDtoInCreate) {
        try {
            Professional professionalAuthenticated = authenticationHelper.tryGetProfessional(httpHeaders);
            JobApplication jobApplication = modelMapper.fromJobApplicationDtoInCreateToJobApplication(
                    professionalAuthenticated, jobApplicationDtoInCreate);
            jobApplicationService.createJobApplication(jobApplication);

            return modelMapper.fromJobApplicationToJobApplicationDtoOut(jobApplication);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PutMapping("/{jobApplicationId}")
    public JobApplicationDtoOut updateJobApplication(@RequestHeader HttpHeaders httpHeaders,
                                               @PathVariable int jobApplicationId,
                                               @Valid @RequestBody JobApplicationDtoInUpdate jobApplicationDtoInUpdate) {
        try {
            Professional professionalAuthenticated =
                    authenticationHelper.tryGetProfessional(httpHeaders);
            JobApplication jobApplicationMapped =
                    modelMapper.fromJobApplicationDtoInUpdateToJobApplication(
                            jobApplicationId,
                            jobApplicationDtoInUpdate);
            jobApplicationService.updateJobApplication(professionalAuthenticated, jobApplicationMapped);

            return modelMapper.fromJobApplicationToJobApplicationDtoOut(jobApplicationMapped);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @DeleteMapping("/{jobApplicationId}")
    public void deleteJobApplication(@RequestHeader HttpHeaders httpHeaders,
                                     @PathVariable int jobApplicationId) {
        try {
            Professional professionalAuthenticated =
                    authenticationHelper.tryGetProfessional(httpHeaders);
            JobApplication jobApplicationToDelete = jobApplicationService.getJobApplicationById(jobApplicationId);

            jobApplicationService.deleteJobApplication(jobApplicationToDelete, professionalAuthenticated);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
