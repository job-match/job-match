package com.project.jobmatch.controllers.mvc.company;

import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.models.Company;
import com.project.jobmatch.models.dto.CompanyDtoSearch;
import com.project.jobmatch.services.interfaces.CompanyService;
import com.project.jobmatch.services.interfaces.JobAdService;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/professional-portal/companies")
public class CompanyMvcControllerForProfessionals {

    public static final String COMPANIES_PER_PAGE = "6";

    private final CompanyService companyService;
    private final JobAdService jobAdService;

    public CompanyMvcControllerForProfessionals(CompanyService companyService, JobAdService jobAdService) {
        this.companyService = companyService;
        this.jobAdService = jobAdService;
    }

    //TODO This is not needed
    @ModelAttribute
    public void getJobAdsByCompanyId(@PathVariable(required = false) Integer id, Model model) {
        if (id != null) {
            try {
                model.addAttribute("jobAdsOfCompany", jobAdService.getJobAdsByCompanyId(id));
            } catch (EntityNotFoundException e) {
                model.addAttribute("jobAdsOfCompany", new ArrayList<>());
            }
        }
    }

    @GetMapping
    public String getAllCompanies(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = COMPANIES_PER_PAGE) int size,
                                  @RequestParam(defaultValue = "name") String sortField,
                                  @RequestParam(defaultValue = "asc") String sortDirection,
                                  @ModelAttribute("companyDtoSearch") CompanyDtoSearch companyDtoSearch,
                                  Model model,
                                  HttpSession httpSession) {
        //TODO Authenticate when ready

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
        model.addAttribute("currentPage", companyPage.getNumber());
        model.addAttribute("totalPages", companyPage.getTotalPages());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);

        return "company/companies-view";
    }

    @GetMapping("/{id}")
    public String showSingleCompany(@PathVariable int id, Model model, HttpSession httpSession) {
        //TODO Authenticate when ready

        try {
            model.addAttribute("company", companyService.getCompanyById(id));
            model.addAttribute("jobAdsOfCompany", jobAdService.getJobAdsByCompanyId(id));

            return "company/company-view";
        } catch (EntityNotFoundException e) {
            //TODO Create error view
            throw new UnsupportedOperationException("Create empty view");
        }
    }

}
