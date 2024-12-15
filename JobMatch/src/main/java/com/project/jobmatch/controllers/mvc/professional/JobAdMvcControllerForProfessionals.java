package com.project.jobmatch.controllers.mvc.professional;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.helpers.AuthenticationHelper;
import com.project.jobmatch.models.Company;
import com.project.jobmatch.models.JobAd;
import com.project.jobmatch.models.Professional;
import com.project.jobmatch.models.dto.JobAdDtoSearch;
import com.project.jobmatch.services.interfaces.JobAdService;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/professional-portal/job-ads")
public class JobAdMvcControllerForProfessionals {

    public static final String JOB_ADS_BY_PAGE = "6";
    private final JobAdService jobAdService;
    private final AuthenticationHelper authenticationHelper;

    public JobAdMvcControllerForProfessionals(JobAdService jobAdService, AuthenticationHelper authenticationHelper) {
        this.jobAdService = jobAdService;
        this.authenticationHelper = authenticationHelper;
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
    public String getAllJobAds(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = JOB_ADS_BY_PAGE) int size,
                               @RequestParam(defaultValue = "positionTitle") String sortField,
                               @RequestParam(defaultValue = "asc") String sortDirection,
                               @ModelAttribute("jobAdDtoSearch")JobAdDtoSearch jobAdDtoSearch,
                               Model model,
                               HttpSession httpSession) {
        //TODO Authenticate when ready

        if ("location".equals(sortField)) {
            sortField = "location.name";
        }

        Sort sort = Sort.by(sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);

        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<JobAd> jobAdPage = jobAdService.searchJobAdsPaginated(
                jobAdDtoSearch.getPositionTitle(),
                jobAdDtoSearch.getLocation(),
                jobAdDtoSearch.getMinSalary(),
                jobAdDtoSearch.getMaxSalary(),
                jobAdDtoSearch.getRequirement(),
                pageRequest);

        model.addAttribute("jobAdsPaged", jobAdPage.getContent());
        model.addAttribute("jobAdsAllSize", jobAdPage.getTotalElements());
        model.addAttribute("currentPage", jobAdPage.getNumber());
        model.addAttribute("totalPages", jobAdPage.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);

        return "job-ad/job-ads-view";
    }

    @GetMapping("/{id}")
    public String showSingleJobAd(@PathVariable int id, Model model, HttpSession httpSession) {

        try {
            authenticationHelper.tryGetCurrentProfessional(httpSession);
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
}
