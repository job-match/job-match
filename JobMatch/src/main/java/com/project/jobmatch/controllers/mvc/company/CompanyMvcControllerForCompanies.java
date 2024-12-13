package com.project.jobmatch.controllers.mvc.company;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.helpers.AuthenticationHelper;
import com.project.jobmatch.helpers.ModelMapper;
import com.project.jobmatch.models.*;
import com.project.jobmatch.models.dto.CompanyDtoInUpdate;
import com.project.jobmatch.models.dto.CompanyDtoOutUpdate;
import com.project.jobmatch.models.dto.ProfessionalDtoInUpdate;
import com.project.jobmatch.models.dto.ProfessionalDtoOutUpdate;
import com.project.jobmatch.services.CloudinaryImage;
import com.project.jobmatch.services.interfaces.*;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    private final CloudinaryService cloudinaryService;
    private final LocationService locationService;
    private final ModelMapper modelMapper;

    @Autowired
    public CompanyMvcControllerForCompanies(AuthenticationHelper authenticationHelper,
                                            CompanyService companyService,
                                            ProfessionalService professionalService,
                                            JobApplicationService jobApplicationService,
                                            JobAdService jobAdService,
                                            MatchService matchService,
                                            CloudinaryService cloudinaryService,
                                            LocationService locationService,
                                            ModelMapper modelMapper) {
        this.authenticationHelper = authenticationHelper;
        this.companyService = companyService;
        this.professionalService = professionalService;
        this.jobApplicationService = jobApplicationService;
        this.jobAdService = jobAdService;
        this.matchService = matchService;
        this.cloudinaryService = cloudinaryService;
        this.locationService = locationService;
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

    @ModelAttribute("locations")
    public List<Location> populateLocations() {
        return locationService.getAllLocations();
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

    @GetMapping("/update")
    public String getUpdateCompanyProfile(Model model, HttpSession session) {
        Company company;
        try {
            company = authenticationHelper.tryGetCurrentCompany(session);
        } catch (AuthorizationException e) {
            return "redirect:/auth/company-portal/login";
        }

        CompanyDtoOutUpdate companyDtoOutUpdate = modelMapper.fromCompanyToCompanyDtoOutUpdate(company);
        model.addAttribute("company", companyDtoOutUpdate);
        return "company/company-update-profile-view";
    }

    @PostMapping("/update")
    public String updateCompanyProfile(@Valid @ModelAttribute("company") CompanyDtoInUpdate companyDtoInUpdate,
                                    BindingResult bindingResult,
                                    @RequestParam("companyPicture") MultipartFile companyPicture,
                                    Model model,
                                    HttpSession session) {

        Company companyAuthenticated;

        try {
            companyAuthenticated = authenticationHelper.tryGetCurrentCompany(session);
        } catch (AuthorizationException e) {
            return "redirect:/auth/company-portal/login";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("locations", populateLocations());
            model.addAttribute("company", companyDtoInUpdate);
            return "company/company-update-profile-view";
        }

        try {
            Company updatedCompany =
                    modelMapper.fromCompanyDtoInUpdateToCompany(companyAuthenticated.getId(), companyDtoInUpdate);

            if (!companyPicture.isEmpty()) {
                CloudinaryImage cloudinaryImage = cloudinaryService.upload(companyPicture);
                companyService.uploadPictureToCompany(companyAuthenticated, updatedCompany, cloudinaryImage);

            } else {
                companyService.updateCompany(companyAuthenticated, updatedCompany);
            }


        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());

            return "error";
        } catch (IOException e) {
            model.addAttribute("statusCode", HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase());
            model.addAttribute("error", e.getMessage());

            return "error";
        }

        CompanyDtoOutUpdate companyDtoOutUpdate = modelMapper.fromCompanyToCompanyDtoOutUpdate(companyAuthenticated);
        model.addAttribute("company", companyDtoOutUpdate);

        return "company/company-update-profile-view";
    }

    @GetMapping("/delete/{id}")
    public String deleteCompany(@PathVariable int id, Model model, HttpSession session) {
        Company companyAuthenticated;
        try {
            companyAuthenticated = authenticationHelper.tryGetCurrentCompany(session);
        } catch (AuthorizationException e) {
            return "redirect:/auth/company-portal/login";
        }

        try {
            Company companyToDelete = companyService.getCompanyById(id);

            companyService.deleteCompany(companyToDelete, companyAuthenticated);

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
