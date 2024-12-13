package com.project.jobmatch.controllers.mvc.company;

import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.models.JobApplication;
import com.project.jobmatch.models.dto.JobAdDtoSearch;
import com.project.jobmatch.models.dto.JobApplicationDtoSearch;
import com.project.jobmatch.services.interfaces.JobApplicationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/company-portal/job-applications")
public class JobApplicationMvcControllerForCompanies {

    public static final String JOB_APPLICATIONS_BY_PAGE = "6";

    private final JobApplicationService jobApplicationService;

    public JobApplicationMvcControllerForCompanies(JobApplicationService jobApplicationService) {
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

    @GetMapping
    public String getAllJobApplications(@RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = JOB_APPLICATIONS_BY_PAGE) int size,
                                        @RequestParam(defaultValue = "location") String sortField,
                                        @RequestParam(defaultValue = "asc") String sortDirection,
                                        @ModelAttribute("JobApplicationDtoSearch")JobApplicationDtoSearch jobApplicationDtoSearch,
                                        Model model,
                                        HttpSession httpSession) {
        //TODO Authenticate when ready

        if ("location".equals(sortField)) {
            sortField = "location.name";
        }

        Sort sort = Sort.by(sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);

        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<JobApplication> jobAppPage = jobApplicationService.searchJobApplicationsPaginated(
                jobApplicationDtoSearch.getLocation(),
                jobApplicationDtoSearch.getMinSalary(),
                jobApplicationDtoSearch.getMaxSalary(),
                jobApplicationDtoSearch.getSkill(),
                jobApplicationDtoSearch.getKeyword(),
                pageRequest);

        model.addAttribute("jobAppPage", jobAppPage.getContent());
        model.addAttribute("jobAppAllSize", jobAppPage.getTotalElements());
        model.addAttribute("currentPage", jobAppPage.getNumber());
        model.addAttribute("totalPages", jobAppPage.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);

        return "job-application/job-applications-view";
    }

    @GetMapping("/{id}")
    public String showSingleJobApplication(@PathVariable int id, Model model, HttpSession httpSession) {
        //TODO Authenticate when ready

        try {
            model.addAttribute("jobApp", jobApplicationService.getJobApplicationById(id));

            return "job-application/job-application-view";
        } catch (EntityNotFoundException e) {
            //TODO Create error view
            throw new UnsupportedOperationException("Create empty view");
        }
    }
}


















