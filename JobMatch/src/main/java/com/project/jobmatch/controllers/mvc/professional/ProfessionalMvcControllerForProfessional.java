package com.project.jobmatch.controllers.mvc.professional;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.helpers.AuthenticationHelper;
import com.project.jobmatch.helpers.ModelMapper;
import com.project.jobmatch.models.JobAd;
import com.project.jobmatch.models.JobApplication;
import com.project.jobmatch.models.Professional;
import com.project.jobmatch.services.interfaces.CloudinaryService;
import com.project.jobmatch.services.interfaces.JobApplicationService;
import com.project.jobmatch.services.interfaces.MatchService;
import com.project.jobmatch.services.interfaces.ProfessionalService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/professional-portal/professionals")
public class ProfessionalMvcControllerForProfessional {

    private final AuthenticationHelper authenticationHelper;
    private final ProfessionalService professionalService;
    private final JobApplicationService jobApplicationService;
    private final CloudinaryService cloudinaryService;
    private final MatchService matchService;
    private final ModelMapper modelMapper;


    @Autowired
    public ProfessionalMvcControllerForProfessional(AuthenticationHelper authenticationHelper,
                                                    ProfessionalService professionalService,
                                                    JobApplicationService jobApplicationService,
                                                    CloudinaryService cloudinaryService,
                                                    ModelMapper modelMapper,
                                                    MatchService matchService) {
        this.authenticationHelper = authenticationHelper;
        this.professionalService = professionalService;
        this.jobApplicationService = jobApplicationService;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
        this.matchService = matchService;
    }

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }

    @GetMapping("/profile")
    public String getProfessionalProfileInfo(Model model, HttpSession session) {
        Professional professional;
        List<JobApplication> activeJobApplications;
        List<JobAd> matchedJobAds;

        try {
//            professional = authenticationHelper.tryGetCurrentProfessional(session);
            professional = professionalService.getProfessionalById(21);
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
}
