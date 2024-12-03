package com.project.jobmatch.controllers.mvc;

import com.project.jobmatch.models.Company;
import com.project.jobmatch.models.Professional;
import com.project.jobmatch.services.interfaces.CompanyService;
import com.project.jobmatch.services.interfaces.ProfessionalService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class HomeMvcController {

    private final CompanyService companyService;
    private final ProfessionalService professionalService;

    public HomeMvcController(CompanyService companyService, ProfessionalService professionalService) {
        this.companyService = companyService;
        this.professionalService = professionalService;
    }

    @GetMapping
    public String showHomePage(Model model) {
        List<Company> allCompanies = companyService.getAllCompanies();
        List<Professional> allProfessionals = professionalService.getAllProfessionals();

        model.addAttribute("allCompanies", allCompanies);
        model.addAttribute("allProfessionals", allProfessionals);

        return "index";

    }
}
