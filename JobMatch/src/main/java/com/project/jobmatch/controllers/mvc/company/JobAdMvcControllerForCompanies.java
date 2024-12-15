package com.project.jobmatch.controllers.mvc.company;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.helpers.AuthenticationHelper;
import com.project.jobmatch.helpers.ModelMapper;
import com.project.jobmatch.models.Company;
import com.project.jobmatch.models.JobAd;
import com.project.jobmatch.models.JobApplication;
import com.project.jobmatch.models.Professional;
import com.project.jobmatch.services.interfaces.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/company-portal/job-ads")
public class JobAdMvcControllerForCompanies {

    private final AuthenticationHelper authenticationHelper;
    private final JobAdService jobAdService;
    private final StatusService statusService;
    private final LocationService locationService;
    private final ModelMapper modelMapper;
    private final SkillService skillService;

    public JobAdMvcControllerForCompanies(AuthenticationHelper authenticationHelper,
                                          JobAdService jobAdService,
                                          StatusService statusService,
                                          LocationService locationService,
                                          SkillService skillService,
                                          ModelMapper modelMapper) {
        this.authenticationHelper = authenticationHelper;
        this.jobAdService = jobAdService;
        this.locationService = locationService;
        this.statusService = statusService;
        this.skillService = skillService;
        this.modelMapper = modelMapper;
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

    @GetMapping("/{id}")
    public String showSingleJobAd(@PathVariable int id, Model model, HttpSession httpSession) {
        try {
            authenticationHelper.tryGetCurrentCompany(httpSession);
            model.addAttribute("jobAd", jobAdService.getJobAdById(id));

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

            return "redirect:/logout";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "error";
        } catch (AuthorizationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            return "error";
        }
    }


}
