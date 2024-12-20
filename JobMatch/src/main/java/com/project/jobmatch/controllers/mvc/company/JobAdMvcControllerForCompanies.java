package com.project.jobmatch.controllers.mvc.company;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.helpers.AuthenticationHelper;
import com.project.jobmatch.helpers.ModelMapper;
import com.project.jobmatch.helpers.ParsingHelper;
import com.project.jobmatch.models.*;
import com.project.jobmatch.models.dto.JobAdDtoInCreate;
import com.project.jobmatch.models.dto.JobAdDtoUpdate;
import com.project.jobmatch.services.interfaces.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/company-portal/job-ads")
public class JobAdMvcControllerForCompanies {

    private final AuthenticationHelper authenticationHelper;
    private final JobAdService jobAdService;
    private final StatusService statusService;
    private final LocationService locationService;
    private final ModelMapper modelMapper;
    private final RequirementService requirementService;
    private final JobApplicationService jobApplicationService;
    private final MatchService matchService;

    public JobAdMvcControllerForCompanies(AuthenticationHelper authenticationHelper,
                                          JobAdService jobAdService,
                                          StatusService statusService,
                                          LocationService locationService,
                                          SkillService skillService,
                                          RequirementService requirementService,
                                          ModelMapper modelMapper, JobApplicationService jobApplicationService, MatchService matchService) {
        this.authenticationHelper = authenticationHelper;
        this.jobAdService = jobAdService;
        this.locationService = locationService;
        this.statusService = statusService;
        this.requirementService = requirementService;
        this.modelMapper = modelMapper;
        this.jobApplicationService = jobApplicationService;
        this.matchService = matchService;
    }

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }

    @ModelAttribute("isProfessional")
    public boolean populateIsProfessional(HttpSession httpSession) {
        Object currentUser = httpSession.getAttribute("currentUser");
        String currentUserClass = (String) httpSession.getAttribute("currentUserClass");

        return currentUser != null && currentUserClass.equals("Professional");
    }

    @ModelAttribute("isCompany")
    public boolean populateIsCompany(HttpSession httpSession) {
        Object currentUser = httpSession.getAttribute("currentUser");
        String currentUserClass = (String) httpSession.getAttribute("currentUserClass");

        return currentUser != null && currentUserClass.equals("Company");
    }

    @ModelAttribute("currentUserUsername")
    public String populateCurrentUserUsername(HttpSession httpSession) {
        Object currentUser = httpSession.getAttribute("currentUser");
        if (currentUser != null) {
            return httpSession.getAttribute("currentUser").toString();
        }

        return "";
    }

    @ModelAttribute("locations")
    public List<Location> populateLocations() {
        return locationService.getAllLocations();
    }

    @ModelAttribute("statuses")
    public List<Status> populateStatuses() {
        Status active = statusService.getStatusByType("Active");
        Status archived = statusService.getStatusByType("Archived");

        List<Status> statuses = new ArrayList<>();
        statuses.add(active);
        statuses.add(archived);

        return statuses;
    }

    @GetMapping("/{id}")
    public String showSingleJobAd(@PathVariable int id, Model model, HttpSession httpSession) {
        Company company;
        JobAd jobAd;

        try {
            company = authenticationHelper.tryGetCurrentCompany(httpSession);
            jobAd = jobAdService.getJobAdById(id);
            model.addAttribute("jobAd", jobAd);
            model.addAttribute("isCompanyOwner", jobAdService.checkIfOwnerOfJobAd(company, jobAd));

            return "job-ad/job-ad-view";

        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "error";

        } catch (AuthorizationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping()
    public String showCreateJobAdView(Model model) {

        model.addAttribute("jobAd", new JobAdDtoInCreate());
        return "job-ad/job-ad-create";
    }

    @PostMapping()
    public String createJobAd(@Valid @ModelAttribute("jobAd") JobAdDtoInCreate jobAdDtoInCreate,
                              BindingResult bindingResult,
                              @RequestParam(name = "requirements", required = false) String requirementsInput,
                              Model model,
                              HttpSession session) {
        Company companyAuthenticated;
        try {
            companyAuthenticated = authenticationHelper.tryGetCurrentCompany(session);
        } catch (AuthorizationException e) {
            return "redirect:/company-profile/login";
        }

        if (bindingResult.hasErrors()) {
            return "job-ad/job-ad-create";
        }

        try {
            Set<String> requirementsTypes = ParsingHelper.fromStringToSetStrings(requirementsInput);
            jobAdDtoInCreate.setRequirements(requirementsTypes);
            Set<Requirement> requirements = requirementService.findRequirementsByType(requirementsTypes);

            JobAd jobAd = modelMapper.fromJobAdDtoIn(jobAdDtoInCreate, companyAuthenticated);
            jobAd.setRequirements(requirements);

            jobAdService.createJobAd(jobAd);

            return "redirect:/company-portal/companies/profile";

        } catch (AuthorizationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/{id}/update")
    public String showUpdateJobAdView(@PathVariable int id, Model model, HttpSession session) {
        try {
            authenticationHelper.tryGetCurrentCompany(session);
        } catch (AuthorizationException e) {
            return "redirect:/company-profile/login";
        }

        try {
            JobAd jobAd = jobAdService.getJobAdById(id);
            Set<String> requirementsSetStrings = ParsingHelper.fromSetRequirementsToSetStrings(jobAd.getRequirements());

            JobAdDtoUpdate jobAdDtoUpdate = modelMapper.fromJobAdToJobAdDtoUpdate(jobAd);
            jobAdDtoUpdate.setRequirements(requirementsSetStrings);

            String requirementsForDisplay = String.join(", ", requirementsSetStrings);

            model.addAttribute("jobAdId", id);
            model.addAttribute("jobAd", jobAdDtoUpdate);
            model.addAttribute("requirementsForDisplay", requirementsForDisplay);

            return "job-ad/job-ad-update";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

    @PostMapping("/{id}/update")
    public String updateJobAd(@PathVariable int id,
                              @Valid @ModelAttribute("jobAd") JobAdDtoUpdate jobAdDtoUpdate,
                              BindingResult bindingResult,
                              Model model,
                              HttpSession session,
                              @RequestParam(name = "requirements", required = false) String requirementsInput) {
        Company companyAuthenticated;
        try {
            companyAuthenticated = authenticationHelper.tryGetCurrentCompany(session);
        } catch (AuthorizationException e) {
            return "redirect:/company-profile/login";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("jobAdId", id);
            return "job-ad/job-ad-update";
        }

        try {
            Set<String> requirementsNames = ParsingHelper.fromStringToSetStrings(requirementsInput);
            jobAdDtoUpdate.setRequirements(requirementsNames);
            Set<Requirement> requirements = requirementService.findRequirementsByType(requirementsNames);

            JobAd jobAd = modelMapper.fromJobAdDtoUpdateToJobAd(id, jobAdDtoUpdate);
            jobAd.setRequirements(requirements);

            jobAdService.updateJobAd(jobAd, companyAuthenticated);

            return "redirect:/company-portal/job-ads/" + id;

        } catch (AuthorizationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }


    @GetMapping("/{id}/delete")
    public String deleteJobAd(@PathVariable int id, Model model, HttpSession session) {
        Company companyAuthenticated;
        try {
            companyAuthenticated = authenticationHelper.tryGetCurrentCompany(session);
        } catch (AuthorizationException e) {
            return "redirect:/auth/company-portal/login";
        }

        try {
            JobAd jobAdToDelete = jobAdService.getJobAdById(id);

            jobAdService.deleteJobAd(jobAdToDelete, companyAuthenticated);

            return "redirect:/company-portal/companies/profile";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "error";
        } catch (AuthorizationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            return "error";
        }
    }

    @GetMapping("{id}/match-requests")
    public String showJobAdMatchRequestsView(@PathVariable int id,
                                             Model model,
                                             HttpSession httpSession) {
        try {
            authenticationHelper.tryGetCurrentCompany(httpSession);

            model.addAttribute("jobAd", jobAdService.getJobAdById(id));

            return "job-ad/job-ad-match-requests";
        } catch (AuthorizationException e) {
            return "redirect:/auth/company-portal/login";
        }
    }

    @PostMapping("/{jobAdId}/accept-match-request/{jobAppId}")
    public String acceptMatchRequest(@PathVariable int jobAdId,
                                     @PathVariable int jobAppId,
                                     Model model,
                                     HttpSession httpSession) {
        Company company;

        try {
            company = authenticationHelper.tryGetCurrentCompany(httpSession);

        } catch (AuthorizationException e) {
            return "redirect:/auth/company-portal/login";
        }

        try {
            JobAd jobAd = jobAdService.getJobAdById(jobAdId);
            JobApplication jobApplicationToAccept = jobApplicationService.getJobApplicationById(jobAppId);

            matchService.confirmMatchWithJobApplication(jobAd, jobApplicationToAccept, company);

            return "redirect:/company-portal/job-ads/" + jobAdId + "/match-requests";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "error-view";
        } catch (AuthorizationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "error-view";
        }
    }

    @PostMapping("/{jobAdId}/reject-match-request/{jobAppId}")
    public String rejectMatchRequest(@PathVariable int jobAdId,
                                     @PathVariable int jobAppId,
                                     Model model,
                                     HttpSession httpSession) {
        Company company;

        try {
            company = authenticationHelper.tryGetCurrentCompany(httpSession);

        } catch (AuthorizationException e) {
            return "redirect:/auth/professional-portal/login";
        }

        try {
            JobAd jobAd = jobAdService.getJobAdById(jobAdId);
            JobApplication jobApplicationToReject = jobApplicationService.getJobApplicationById(jobAppId);

            matchService.rejectMatchWithJobApplication(jobAd, jobApplicationToReject, company);

            return "redirect:/company-portal/job-ads/" + jobAdId + "/match-requests";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "error-view";
        } catch (AuthorizationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "error-view";
        }
    }
}
