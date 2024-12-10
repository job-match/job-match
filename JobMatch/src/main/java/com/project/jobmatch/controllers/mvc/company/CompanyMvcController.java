package com.project.jobmatch.controllers.mvc.company;

import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.services.interfaces.CompanyService;
import com.project.jobmatch.services.interfaces.JobAdService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/companies")
public class CompanyMvcController {

    private final CompanyService companyService;
    private final JobAdService jobAdService;

    public CompanyMvcController(CompanyService companyService, JobAdService jobAdService) {
        this.companyService = companyService;
        this.jobAdService = jobAdService;
    }

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
    public String getAllCompanies(Model model, HttpSession httpSession) {
        //TODO Authenticate when ready

        model.addAttribute("allCompanies", companyService.getAllCompanies());

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
