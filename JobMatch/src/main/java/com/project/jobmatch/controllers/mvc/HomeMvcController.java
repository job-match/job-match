package com.project.jobmatch.controllers.mvc;

import com.project.jobmatch.models.*;
import com.project.jobmatch.services.interfaces.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping
    public String showHomePage(Model model) {
        List<Company> allCompanies = companyService.getAllCompanies();
        List<Professional> allProfessionals = professionalService.getAllProfessionals();
        List<JobAd> allJobAds = jobAdService.getAll();
        List<JobApplication> allJobApplications = jobApplicationService.getAllJobApplications();
        List<Location> allLocations = locationService.getAllLocations();
        List<JobAd> sixMostRecentJobAds = jobAdService.getSixMostRecentJobAds();

        Map<Location, Integer> jobCountByLocation = new HashMap<>();

        for (Location location : allLocations) {
            List<JobAd> jobAdsForLocation = jobAdService.getJobAdsByLocation(location);
            jobCountByLocation.put(location, jobAdsForLocation.size());
        }

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

        model.addAttribute("allCompanies", allCompanies);
        model.addAttribute("allProfessionals", allProfessionals);
        model.addAttribute("allJobAds", allJobAds);
        model.addAttribute("allJobApplications", allJobApplications);

        return "about";
    }
}
