package com.project.jobmatch.controllers.mvc;

import com.project.jobmatch.models.*;
import com.project.jobmatch.services.interfaces.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class HomeMvcController {

    private final CompanyService companyService;
    private final ProfessionalService professionalService;
    private final JobAdService jobAdService;
    private final JobApplicationService jobApplicationService;
    private final LocationService locationService;

    public HomeMvcController(CompanyService companyService, ProfessionalService professionalService, JobAdService jobAdService, JobApplicationService jobApplicationService, LocationService locationService) {
        this.companyService = companyService;
        this.professionalService = professionalService;
        this.jobAdService = jobAdService;
        this.jobApplicationService = jobApplicationService;
        this.locationService = locationService;
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

    @GetMapping
    public String showHomePage(Model model) {
        List<Company> allCompanies = companyService.getAllCompanies();
        List<Professional> allProfessionals = professionalService.getAllProfessionals();
        List<JobAd> allJobAds = jobAdService.getAll();
        List<JobApplication> allJobApplications = jobApplicationService.getAllJobApplications();
        List<Location> allLocations = locationService.getAllLocations();
        List<JobAd> sixMostRecentJobAds = jobAdService.getSixMostRecentJobAds();
        Company retailBoost = companyService.getCompanyById(9);
        Company ecoBuilders = companyService.getCompanyById(6);
        Company greenSolutions = companyService.getCompanyById(2);
        Company devExperts = companyService.getCompanyById(1);

        Map<Location, Integer> jobCountByLocation = new HashMap<>();

        for (Location location : allLocations) {
            List<JobAd> jobAdsForLocation = jobAdService.getJobAdsByLocation(location);
            jobCountByLocation.put(location, jobAdsForLocation.size());
        }

        model.addAttribute("retailBoost", retailBoost);
        model.addAttribute("ecoBuilders", ecoBuilders);
        model.addAttribute("greenSolutions", greenSolutions);
        model.addAttribute("devExperts", devExperts);
        model.addAttribute("allCompanies", allCompanies);
        model.addAttribute("allProfessionals", allProfessionals);
        model.addAttribute("allJobAds", allJobAds);
        model.addAttribute("allJobApplications", allJobApplications);
        model.addAttribute("allLocations", allLocations);
        model.addAttribute("jobCountByLocation", jobCountByLocation);
        model.addAttribute("sixMostRecentJobAds", sixMostRecentJobAds);

        return "index";

    }

    @GetMapping("/about")
    public String showAboutPage(Model model) {
        List<Company> allCompanies = companyService.getAllCompanies();
        List<Professional> allProfessionals = professionalService.getAllProfessionals();
        List<JobAd> allJobAds = jobAdService.getAll();
        List<JobApplication> allJobApplications = jobApplicationService.getAllJobApplications();
        Professional nikolay = professionalService.getProfessionalById(21);
        Professional kiril = professionalService.getProfessionalById(1);
        Professional anna = professionalService.getProfessionalById(9);

        model.addAttribute("allCompanies", allCompanies);
        model.addAttribute("allProfessionals", allProfessionals);
        model.addAttribute("allJobAds", allJobAds);
        model.addAttribute("allJobApplications", allJobApplications);
        model.addAttribute("nikolay", nikolay);
        model.addAttribute("kiril", kiril);
        model.addAttribute("anna", anna);

        return "about";
    }

    @GetMapping("/logout")
    public String handleLogout(HttpSession session) {
        session.removeAttribute("currentUser");
        return "redirect:/";
    }
}
