package com.project.jobmatch.controllers.mvc.company;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.helpers.AuthenticationHelper;
import com.project.jobmatch.models.Company;
import com.project.jobmatch.models.JobAd;
import com.project.jobmatch.models.JobApplication;
import com.project.jobmatch.models.Professional;
import com.project.jobmatch.services.interfaces.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/company-portal/companies")
public class CompanyMvcControllerForCompanies {

    private final AuthenticationHelper authenticationHelper;
    private final CompanyService companyService;
    private final ProfessionalService professionalService;
    private final JobApplicationService jobApplicationService;
    private final JobAdService jobAdService;
    private final MatchService matchService;

    @Autowired
    public CompanyMvcControllerForCompanies(AuthenticationHelper authenticationHelper, CompanyService companyService, ProfessionalService professionalService, JobApplicationService jobApplicationService, JobAdService jobAdService, MatchService matchService) {
        this.authenticationHelper = authenticationHelper;
        this.companyService = companyService;
        this.professionalService = professionalService;
        this.jobApplicationService = jobApplicationService;
        this.jobAdService = jobAdService;
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

    @GetMapping("/profile")
    public String getCompanyProfileInfo(Model model, HttpSession httpSession) {

        Company company;
        List<JobAd> activeJobAds;
        List<JobApplication> matchedJobApps;

        try {
            company = authenticationHelper.tryGetCurrentCompany(httpSession);
            activeJobAds = jobAdService.getAllActiveJobAdsOfCompany(company);
            matchedJobApps = matchService.getMatchedJobApplications(company);
        } catch (AuthorizationException e) {
            return "redirect:/auth/company-portal/login";
        }

        model.addAttribute("company", company);
        model.addAttribute("activeJobAds", activeJobAds);
        model.addAttribute("matchedJobApps", matchedJobApps);

        return "company/company-profile-view";
    }
}
