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

    @GetMapping("/profile")
    public String getCompanyProfileInfo(Model model, HttpSession httpSession) {
        //TODO Uncomment the below code once login for company is ready

        Professional professional;
        Company company;
        List<JobApplication> activeJobApplications;
        List<JobApplication> matchedJobApps;

        company = companyService.getCompanyById(1);
        professional = professionalService.getProfessionalById(1);
        matchedJobApps = matchService.getMatchedJobApplications(company);

//         try {
//             Company company = authenticationHelper.tryGetCurrentCompany(httpSession);
//         } catch (AuthorizationException e) {
//             return "redirect:/auth/company-portal/login";
//         }

        model.addAttribute("company", company);
        model.addAttribute("professional", professional);
        model.addAttribute("activeJobAds", jobAdService.getAllActiveJobAdsOfCompany(company));
        model.addAttribute("matchedJobApps", matchedJobApps);

        return "company/company-profile-view";
    }
}