package com.project.jobmatch.controllers.mvc.professional;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.helpers.AuthenticationHelper;
import com.project.jobmatch.helpers.ModelMapper;
import com.project.jobmatch.models.*;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/professional-portal/professionals")
public class ProfessionalMvcControllerForProfessional {

    private final AuthenticationHelper authenticationHelper;
    private final ProfessionalService professionalService;
    private final JobApplicationService jobApplicationService;
    private final CloudinaryService cloudinaryService;
    private final MatchService matchService;
    private final LocationService locationService;
    private final StatusService statusService;
    private final ModelMapper modelMapper;


    @Autowired
    public ProfessionalMvcControllerForProfessional(AuthenticationHelper authenticationHelper,
                                                    ProfessionalService professionalService,
                                                    JobApplicationService jobApplicationService,
                                                    CloudinaryService cloudinaryService,
                                                    ModelMapper modelMapper,
                                                    LocationService locationService,
                                                    StatusService statusService,
                                                    MatchService matchService) {
        this.authenticationHelper = authenticationHelper;
        this.professionalService = professionalService;
        this.jobApplicationService = jobApplicationService;
        this.cloudinaryService = cloudinaryService;
        this.locationService = locationService;
        this.statusService = statusService;
        this.modelMapper = modelMapper;
        this.matchService = matchService;
    }

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }

    @ModelAttribute("locations")
    public List<Location> populateLocations() {
        return locationService.getAllLocations();
    }

    @ModelAttribute("statuses")
    public List<Status> populateStatuses() {
        Status active = statusService.getStatusByType("Active");
        Status busy = statusService.getStatusByType("Busy");

        List<Status> statuses = new ArrayList<>();
        statuses.add(active);
        statuses.add(busy);

        return statuses;
    }

    @GetMapping("/profile")
    public String getProfessionalProfileInfo(Model model, HttpSession session) {
        Professional professional;
        List<JobApplication> activeJobApplications;
        List<JobAd> matchedJobAds;

        try {
            professional = authenticationHelper.tryGetCurrentProfessional(session);
            activeJobApplications = jobApplicationService.getAllActiveJobApplicationsOfProfessional(professional);
            matchedJobAds = matchService.getMatchedJobAds(professional);
        } catch (AuthorizationException e) {
            return "redirect:/auth/professional-portal/login";
        }

        model.addAttribute("professional", professional);
        model.addAttribute("activeJobApplications", activeJobApplications);
        model.addAttribute("matchedJobAds", matchedJobAds);

        return "professional/professional-profile-view";
    }

    @GetMapping("/update")
    public String getUpdateProfessionalProfile(Model model, HttpSession session) {
        Professional professional;
        try {
            professional = authenticationHelper.tryGetCurrentProfessional(session);
        } catch (AuthorizationException e) {
            return "redirect:/auth/professional-portal/login";
        }

        ProfessionalDtoOutUpdate professionalDtoOutUpdate = modelMapper.fromProfessionalToProfessionalDtoOutUpdate(professional);
        model.addAttribute("professional", professionalDtoOutUpdate);
        return "professional/professional-update-profile-view";
    }

    @PostMapping("/update")
    public String updateUserProfile(@Valid @ModelAttribute("professional") ProfessionalDtoInUpdate professionalDtoInUpdate,
                                    BindingResult bindingResult,
                                    @RequestParam("professionalPicture") MultipartFile professionalPicture,
                                    Model model,
                                    HttpSession session) {

        Professional professionalAuthenticated;

        try {
            professionalAuthenticated = authenticationHelper.tryGetCurrentProfessional(session);
        } catch (AuthorizationException e) {
            return "redirect:/auth/professional-portal/login";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("statuses", populateStatuses());
            model.addAttribute("locations", populateLocations());
            model.addAttribute("professional", professionalDtoInUpdate);
            return "professional/professional-update-profile-view";
        }

        try {
            Professional updatedProfessional =
                    modelMapper.fromProfessionalDtoInUpdateToProfessional(professionalAuthenticated.getId(), professionalDtoInUpdate);

            if (!professionalPicture.isEmpty()) {
                CloudinaryImage cloudinaryImage = cloudinaryService.upload(professionalPicture);
                professionalService.uploadPictureToProfessional(professionalAuthenticated, updatedProfessional, cloudinaryImage);

            } else {
                professionalService.updateProfessional(professionalAuthenticated, updatedProfessional);
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

        ProfessionalDtoOutUpdate professionalDtoOutUpdate = modelMapper.fromProfessionalToProfessionalDtoOutUpdate(professionalAuthenticated);
        model.addAttribute("professional", professionalDtoOutUpdate);

        return "professional/professional-update-profile-view";
    }

    @GetMapping("/delete/{id}")
    public String deleteProfessional(@PathVariable int id, Model model, HttpSession session) {
        Professional professionalAuthenticated;
        try {
            professionalAuthenticated = authenticationHelper.tryGetCurrentProfessional(session);
        } catch (AuthorizationException e) {
            return "redirect:/auth/professional-portal/login";
        }

        try {
            Professional professionalToDelete = professionalService.getProfessionalById(id);

            professionalService.deleteProfessional(professionalToDelete, professionalAuthenticated);

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
