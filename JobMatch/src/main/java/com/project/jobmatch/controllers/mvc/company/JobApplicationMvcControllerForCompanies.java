package com.project.jobmatch.controllers.mvc.company;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.EntityDuplicateException;
import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.exceptions.MatchRequestDeniedException;
import com.project.jobmatch.helpers.AuthenticationHelper;
import com.project.jobmatch.models.Company;
import com.project.jobmatch.models.JobAd;
import com.project.jobmatch.models.JobApplication;
import com.project.jobmatch.models.dto.JobAdDtoSearch;
import com.project.jobmatch.models.dto.JobApplicationDtoSearch;
import com.project.jobmatch.services.interfaces.CompanyService;
import com.project.jobmatch.services.interfaces.JobAdService;
import com.project.jobmatch.services.interfaces.JobApplicationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import static com.project.jobmatch.helpers.MvcControllersConstants.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/company-portal/job-applications")
public class JobApplicationMvcControllerForCompanies {

    public static final String JOB_APPLICATIONS_BY_PAGE = "6";

    private final JobApplicationService jobApplicationService;
    private final AuthenticationHelper authenticationHelper;
    private final CompanyService companyService;
    private final JobAdService jobAdService;

    public JobApplicationMvcControllerForCompanies(JobApplicationService jobApplicationService, AuthenticationHelper authenticationHelper, CompanyService companyService, JobAdService jobAdService) {
        this.jobApplicationService = jobApplicationService;
        this.authenticationHelper = authenticationHelper;
        this.companyService = companyService;
        this.jobAdService = jobAdService;
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

    @ModelAttribute("jobAdsOfCompany")
    public List<JobAd> populateJobAdListOfCompany(HttpSession httpSession) {
        Object currentUser = httpSession.getAttribute("currentUser");
        if (currentUser != null) {
            String currentUsername = currentUser.toString();
            Company company = companyService.getCompanyByUsername(currentUsername);
            if (company != null) {
                return jobAdService.getJobAdsByCompanyId(company.getId());
            }
        }
        return Collections.emptyList();
    }

    @GetMapping
    public String getAllJobApplications(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = JOB_APPLICATIONS_BY_PAGE) int size,
                                        @RequestParam(defaultValue = "location") String sortField,
                                        @RequestParam(defaultValue = "asc") String sortDirection,
                                        @ModelAttribute("JobApplicationDtoSearch")JobApplicationDtoSearch jobApplicationDtoSearch,
                                        Model model,
                                        HttpSession httpSession) {
        try{
            authenticationHelper.tryGetCurrentCompany(httpSession);
        } catch (AuthorizationException e) {
            return "redirect:/auth/company-portal/login";
        }

        if ("location".equals(sortField)) {
            sortField = "location.name";
        }

        Sort sort = Sort.by(sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);

        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<JobApplication> jobAppPage = jobApplicationService.searchJobApplicationsPaginated(
                jobApplicationDtoSearch.getLocation(),
                jobApplicationDtoSearch.getMinSalary(),
                jobApplicationDtoSearch.getMaxSalary(),
                jobApplicationDtoSearch.getSkill(),
                jobApplicationDtoSearch.getKeyword(),
                pageRequest);

        model.addAttribute("jobAppPage", jobAppPage.getContent());
        model.addAttribute("jobAppAllSize", jobAppPage.getTotalElements());
        model.addAttribute("currentPage", jobAppPage.getNumber());
        model.addAttribute("totalPages", jobAppPage.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);

        return "job-application/job-applications-view";
    }

    @GetMapping("/{id}")
    public String showSingleJobApplication(@PathVariable int id, Model model, HttpSession httpSession) {

        try {
            authenticationHelper.tryGetCurrentCompany(httpSession);
            model.addAttribute("jobApp", jobApplicationService.getJobApplicationById(id));

            return "job-application/job-application-view";
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

    @PostMapping("/match-request")
    public ResponseEntity<?> jobAdRequestMatchWithJobApplication(@RequestParam("jobAppId") int jobAppId,
                                                      @RequestParam("jobAdId") int jobAdId,
                                                      Model model,
                                                      HttpSession httpSession) {
        try {
            authenticationHelper.tryGetCurrentCompany(httpSession);

            JobApplication jobApplication = jobApplicationService.getJobApplicationById(jobAppId);
            JobAd jobAd = jobAdService.getJobAdById(jobAdId);

            jobApplicationService.addJobAdToListOfAdMatchRequests(jobApplication, jobAd);

            return ResponseEntity.ok(MATCH_REQUEST_SUBMITTED_SUCCESSFULLY);
        } catch (AuthorizationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(e.getMessage());
        } catch (EntityDuplicateException | MatchRequestDeniedException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(UNEXPECTED_ERROR_MESSAGE);
        }
    }
}


















