package com.project.jobmatch.controllers.mvc.professional;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.EntityDuplicateException;
import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.helpers.AuthenticationHelper;
import com.project.jobmatch.helpers.ModelMapper;
import com.project.jobmatch.helpers.ParsingHelper;
import com.project.jobmatch.models.*;
import com.project.jobmatch.models.dto.JobApplicationDtoInCreate;
import com.project.jobmatch.services.interfaces.JobApplicationService;
import com.project.jobmatch.services.interfaces.LocationService;
import com.project.jobmatch.services.interfaces.SkillService;
import com.project.jobmatch.services.interfaces.StatusService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Controller
@RequestMapping("/professional-portal/job-applications")
public class JobApplicationMvcControllerForProfessionals {

    private final AuthenticationHelper authenticationHelper;
    private final JobApplicationService jobApplicationService;
    private final StatusService statusService;
    private final LocationService locationService;
    private final ModelMapper modelMapper;
    private final SkillService skillService;

    public JobApplicationMvcControllerForProfessionals(AuthenticationHelper authenticationHelper,
                                                       JobApplicationService jobApplicationService,
                                                       StatusService statusService,
                                                       LocationService locationService,
                                                       SkillService skillService,
                                                       ModelMapper modelMapper) {
        this.authenticationHelper = authenticationHelper;
        this.jobApplicationService = jobApplicationService;
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

    @ModelAttribute("locations")
    public List<Location> populateLocations() {
        return locationService.getAllLocations();
    }

    @ModelAttribute("statuses")
    public List<Status> populateStatuses() {
        Status active = statusService.getStatusByType("Active");
        Status hidden = statusService.getStatusByType("Hidden");
        Status privateStatus = statusService.getStatusByType("Private");
        Status matched = statusService.getStatusByType("Matched");

        List<Status> statuses = new ArrayList<>();
        statuses.add(active);
        statuses.add(hidden);
        statuses.add(privateStatus);
        statuses.add(matched);

        return statuses;
    }

    @GetMapping()
    public String showCreateJobApplicationView(Model model) {

        model.addAttribute("jobApplication", new JobApplicationDtoInCreate());
        return "job-application/job-application-create";
    }

    @PostMapping()
    public String createJobApplication(@Valid @ModelAttribute("jobApplication") JobApplicationDtoInCreate jobApplicationDtoInCreate,
                                       BindingResult bindingResult,
                                       @RequestParam(name = "skills", required = false) String skillsInput,
                                       Model model,
                                       HttpSession session) {
        Professional professionalAuthenticated;
        try {
            professionalAuthenticated = authenticationHelper.tryGetCurrentProfessional(session);
        } catch (AuthorizationException e) {
            return "redirect:/professional-profile/login";
        }

        if (bindingResult.hasErrors()) {
            return "job-application/job-application-create";
        }

        try {
            Set<String> skillsTypes = ParsingHelper.fromStringToSetStrings(skillsInput);
            jobApplicationDtoInCreate.setSkills(skillsTypes);
            Set<Skill> skills = skillService.findSkillsByType(skillsTypes);

            JobApplication jobApplication = modelMapper.fromJobApplicationDtoInCreateToJobApplication(
                    professionalAuthenticated, jobApplicationDtoInCreate);
            jobApplication.setSkills(skills);

            jobApplicationService.createJobApplication(jobApplication, professionalAuthenticated);

            return "redirect:/professional-portal/professionals/profile";

        } catch (AuthorizationException e) {
            model.addAttribute("statusCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
}