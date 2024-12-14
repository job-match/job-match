package com.project.jobmatch.controllers.mvc.company;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.helpers.AuthenticationHelper;
import com.project.jobmatch.models.Professional;
import com.project.jobmatch.models.dto.ProfessionalDtoSearch;
import com.project.jobmatch.services.interfaces.JobApplicationService;
import com.project.jobmatch.services.interfaces.ProfessionalService;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/company-portal/professionals")
public class ProfessionalMvcControllerForCompanies {

    public static final String PROFESSIONALS_PER_PAGE = "6";

    private final AuthenticationHelper authenticationHelper;
    private final ProfessionalService professionalService;
    private final JobApplicationService jobApplicationService;

    public ProfessionalMvcControllerForCompanies(AuthenticationHelper authenticationHelper, ProfessionalService professionalService, JobApplicationService jobApplicationService) {
        this.authenticationHelper = authenticationHelper;
        this.professionalService = professionalService;
        this.jobApplicationService = jobApplicationService;
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
    public String getAllProfessionals(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = PROFESSIONALS_PER_PAGE) int size,
                                      @RequestParam(defaultValue = "firstName") String sortField,
                                      @RequestParam(defaultValue = "asc") String sortDirection,
                                      @ModelAttribute("professionalDtoSearch") ProfessionalDtoSearch professionalDtoSearch,
                                      Model model,
                                      HttpSession httpSession) {
        try {
            authenticationHelper.tryGetCurrentCompany(httpSession);
        } catch (AuthorizationException e) {
            return "redirect:/auth/company-portal/login";
        }

        if ("location".equals(sortField)) {
            sortField = "location.name";
        }

        Sort sort = Sort.by(sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);

        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<Professional> professionalPage = professionalService.searchProfessionalsPaginated(
                professionalDtoSearch.getUsername(),
                professionalDtoSearch.getFirstName(),
                professionalDtoSearch.getEmail(),
                professionalDtoSearch.getKeyword(),
                professionalDtoSearch.getLocation(),
                pageRequest);

        model.addAttribute("professionalsPaged", professionalPage.getContent());
        model.addAttribute("professionalsAllSize", professionalPage.getTotalElements());
        model.addAttribute("currentPage", professionalPage.getNumber());
        model.addAttribute("totalPages", professionalPage.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);

        return "professional/professionals-view";
    }

    @GetMapping("/{id}")
    public String showSingleProfessional(@PathVariable int id, Model model, HttpSession httpSession) {
        try {
            authenticationHelper.tryGetCurrentCompany(httpSession);
        } catch (AuthorizationException e) {
            return "redirect:/auth/company-portal/login";
        }

        try {
            model.addAttribute("professional", professionalService.getProfessionalById(id));
            model.addAttribute("jobAppOfProfessional", jobApplicationService.getJobApplicationsByProfessionalId(id));

            return "professional/professional-view";
        } catch (EntityNotFoundException e) {
            //TODO Create error view
            throw new UnsupportedOperationException("Create empty view");
        }
    }
}
