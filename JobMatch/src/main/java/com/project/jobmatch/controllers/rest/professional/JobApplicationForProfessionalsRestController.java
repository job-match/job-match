package com.project.jobmatch.controllers.rest.professional;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.helpers.AuthenticationHelper;
import com.project.jobmatch.helpers.ModelMapper;
import com.project.jobmatch.models.JobAd;
import com.project.jobmatch.models.JobApplication;
import com.project.jobmatch.models.Professional;
import com.project.jobmatch.models.dto.JobApplicationDtoInCreate;
import com.project.jobmatch.models.dto.JobApplicationDtoInUpdate;
import com.project.jobmatch.models.dto.JobApplicationDtoOut;
import com.project.jobmatch.services.interfaces.JobAdService;
import com.project.jobmatch.services.interfaces.JobApplicationService;
import com.project.jobmatch.services.interfaces.MatchService;
import com.project.jobmatch.services.interfaces.ProfessionalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.project.jobmatch.helpers.RestControllersConstants.JOB_APP_STATUS_TO_ACCEPT;
import static com.project.jobmatch.helpers.RestControllersConstants.JOB_APP_STATUS_TO_IGNORE;

@RestController
@RequestMapping("/api/professional-portal/job-applications")
public class JobApplicationForProfessionalsRestController {

    private final AuthenticationHelper authenticationHelper;
    private final JobApplicationService jobApplicationService;
    private final JobAdService jobAdService;
    private final MatchService matchService;
    private final ProfessionalService professionalService;
    private final ModelMapper modelMapper;

    public JobApplicationForProfessionalsRestController(AuthenticationHelper authenticationHelper,
                                                        JobApplicationService jobApplicationService,
                                                        ProfessionalService professionalService,
                                                        JobAdService jobAdService,
                                                        MatchService matchService,
                                                        ModelMapper modelMapper) {
        this.authenticationHelper = authenticationHelper;
        this.jobApplicationService = jobApplicationService;
        this.professionalService = professionalService;
        this.jobAdService = jobAdService;
        this.matchService = matchService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<JobApplicationDtoOut> getAllJobApplications(@RequestHeader HttpHeaders httpHeaders) {
        try {
            authenticationHelper.tryGetProfessional(httpHeaders);
            List<JobApplication> jobApplicationList = jobApplicationService.getAllJobApplications(JOB_APP_STATUS_TO_ACCEPT);

            return modelMapper.fromListJobApplicationToListJobApplicationDtoOut(jobApplicationList);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public JobApplicationDtoOut getJobApplicationById(@RequestHeader HttpHeaders httpHeaders,
                                                      @PathVariable int id) {
        try {
            authenticationHelper.tryGetProfessional(httpHeaders);

            return modelMapper.fromJobApplicationToJobApplicationDtoOut(
                    jobApplicationService.getJobApplicationById(id, JOB_APP_STATUS_TO_IGNORE));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
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
            return modelMapper.fromListJobApplicationToListJobApplicationDtoOut(jobApplications);
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

    @PostMapping("/{jobAppId}/match-requests/{jobAdId}")
    public void confirmMatchWithJobApplication(@RequestHeader HttpHeaders headers,
                                               @PathVariable int jobAdId,
                                               @PathVariable int jobAppId) {
        try {
            Professional professionalAuthenticated = authenticationHelper.tryGetProfessional(headers);
            JobAd jobAd = jobAdService.getJobAdById(jobAdId);
            JobApplication jobApplication = jobApplicationService.getJobApplicationById(jobAppId);

            matchService.confirmMatchWithJobAd(jobAd, jobApplication, professionalAuthenticated);

        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
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
