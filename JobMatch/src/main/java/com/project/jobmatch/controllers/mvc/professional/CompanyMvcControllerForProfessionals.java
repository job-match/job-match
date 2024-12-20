package com.project.jobmatch.controllers.mvc.professional;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.helpers.AuthenticationHelper;
import com.project.jobmatch.models.Company;
import com.project.jobmatch.models.dto.CompanyDtoSearch;
import com.project.jobmatch.services.interfaces.CompanyService;
import com.project.jobmatch.services.interfaces.JobAdService;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/professional-portal/companies")
public class CompanyMvcControllerForProfessionals {

    public static final String COMPANIES_PER_PAGE = "6";

    private final AuthenticationHelper authenticationHelper;
    private final CompanyService companyService;
    private final JobAdService jobAdService;

    public CompanyMvcControllerForProfessionals(AuthenticationHelper authenticationHelper, CompanyService companyService, JobAdService jobAdService) {
        this.authenticationHelper = authenticationHelper;
        this.companyService = companyService;
        this.jobAdService = jobAdService;
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
    public String getAllCompanies(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = COMPANIES_PER_PAGE) int size,
                                  @RequestParam(defaultValue = "name") String sortField,
                                  @RequestParam(defaultValue = "asc") String sortDirection,
                                  @ModelAttribute("companyDtoSearch") CompanyDtoSearch companyDtoSearch,
                                  Model model,
                                  HttpSession httpSession) {
        try {
            authenticationHelper.tryGetCurrentProfessional(httpSession);
        } catch (AuthorizationException e) {
            return "redirect:/auth/professional-portal/login";
        }

        if ("location".equals(sortField)) {
            sortField = "location.name";
        }

        Sort sort = Sort.by(sortDirection.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);

        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<Company> companyPage = companyService.searchCompaniesPaginated(companyDtoSearch.getUsername(),
                companyDtoSearch.getName(),
                companyDtoSearch.getEmail(),
                companyDtoSearch.getKeyword(),
                companyDtoSearch.getLocation(),
                pageRequest);

        model.addAttribute("companiesPaged", companyPage.getContent());
        model.addAttribute("companiesAllSize", companyPage.getTotalElements());
        model.addAttribute("currentPage", companyPage.getNumber());
        model.addAttribute("totalPages", companyPage.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);

        return "company/companies-view";
    }

    @GetMapping("/{id}")
    public String showSingleCompany(@PathVariable int id, Model model, HttpSession httpSession) {
        Company company = companyService.getCompanyById(id);

        try {
            authenticationHelper.tryGetCurrentProfessional(httpSession);
        } catch (AuthorizationException e) {
            return "redirect:/auth/professional-portal/login";
        }

        try {
            model.addAttribute("company", company);
            model.addAttribute("jobAdsOfCompany", jobAdService.getJobAdsByCompanyId(id));
            model.addAttribute("jobAdsOfCompanyActive", jobAdService.getAllActiveJobAdsOfCompany(company));

            return "company/company-view";
        } catch (EntityNotFoundException e) {
            model.addAttribute("statusCode", HttpStatus.NOT_FOUND.getReasonPhrase());
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }

}
