package com.project.jobmatch.controllers.mvc.company;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.EntityDuplicateException;
import com.project.jobmatch.helpers.AuthenticationHelper;
import com.project.jobmatch.helpers.ModelMapper;
import com.project.jobmatch.models.Company;
import com.project.jobmatch.models.Professional;
import com.project.jobmatch.models.dto.CompanyLoginDto;
import com.project.jobmatch.models.dto.CompanyRegisterDto;
import com.project.jobmatch.models.dto.ProfessionalLoginDto;
import com.project.jobmatch.models.dto.ProfessionalRegisterDto;
import com.project.jobmatch.services.interfaces.CompanyService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.project.jobmatch.helpers.MvcControllersConstants.PASSWORD_CONFIRMATION_ERROR_MESSAGE;

@Controller
@RequestMapping("/auth")
public class AuthenticationMvcControllerForCompanies {
    private final CompanyService companyService;
    private final AuthenticationHelper authenticationHelper;
    private final ModelMapper modelMapper;

    public AuthenticationMvcControllerForCompanies(CompanyService companyService,
                                                   AuthenticationHelper authenticationHelper,
                                                   ModelMapper modelMapper) {
        this.companyService = companyService;
        this.authenticationHelper = authenticationHelper;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }

    @GetMapping("/company-portal/login")
    public String showLoginPage(Model model) {
        if (!model.containsAttribute("loginCompany")) {
            model.addAttribute("loginCompany", new CompanyLoginDto());
        }
        if (!model.containsAttribute("registerCompany")) {
            model.addAttribute("registerCompany", new CompanyRegisterDto());
        }
        model.addAttribute("activeTab", "login");
        return "company/login-register";
    }

    @PostMapping("/company-portal/login")
    public String handleLogin(@Valid @ModelAttribute("loginCompany") CompanyLoginDto companyLoginDto,
                              BindingResult bindingResult,
                              HttpSession session,
                              Model model) {
        if (bindingResult.hasErrors()) {
            if (!model.containsAttribute("registerCompany")) {
                model.addAttribute("registerCompany", new CompanyRegisterDto());
            }
            model.addAttribute("activeTab", "login");
            return "company/login-register";
        }

        try {
            authenticationHelper.verifyAuthenticationProfessional(companyLoginDto.getUsername(), companyLoginDto.getPassword());
            session.setAttribute("currentUser", companyLoginDto.getUsername());
            return "redirect:/";
        } catch (AuthorizationException e) {
            bindingResult.rejectValue("username", "auth_error", e.getMessage());
            if (!model.containsAttribute("registerCompany")) {
                model.addAttribute("registerCompany", new CompanyRegisterDto());
            }
            model.addAttribute("activeTab", "login");
            return "company/login-register";
        }
    }

    @GetMapping("/company-portal/register")
    public String showRegisterPage(Model model) {
        if (!model.containsAttribute("loginCompany")) {
            model.addAttribute("loginCompany", new CompanyLoginDto());
        }
        if (!model.containsAttribute("registerCompany")) {
            model.addAttribute("registerCompany", new CompanyRegisterDto());
        }
        return "company/login-register";
    }

    @PostMapping("/company-portal/register")
    public String handleRegister(@Valid @ModelAttribute("registerCompany") CompanyRegisterDto companyRegisterDto,
                                 BindingResult bindingResult,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            if (!model.containsAttribute("loginCompany")) {
                model.addAttribute("loginCompany", new ProfessionalLoginDto());
            }
            model.addAttribute("activeTab", "register");
            return "company/login-register";
        }

        if (!companyRegisterDto.getPassword().equals(companyRegisterDto.getPasswordConfirmation())) {
            bindingResult.rejectValue("passwordConfirmation", "password_error", PASSWORD_CONFIRMATION_ERROR_MESSAGE);
            if (!model.containsAttribute("loginProfessional")) {
                model.addAttribute("loginProfessional", new CompanyLoginDto());
            }
            model.addAttribute("activeTab", "register");
            return "company/login-register";
        }

        try {
            Company company = modelMapper.fromCompanyRegisterDto(companyRegisterDto);
            companyService.registerCompany(company);
            return "redirect:/auth/company-portal/login";
        } catch (EntityDuplicateException e) {
            bindingResult.rejectValue("username", "username_error", e.getMessage());
            if (!model.containsAttribute("loginCompany")) {
                model.addAttribute("loginCompany", new CompanyLoginDto());
            }
            model.addAttribute("activeTab", "register");
            return "company/login-register";
        }
    }

    @GetMapping("/company-portal/logout")
    public String handleLogout(HttpSession session) {
        session.removeAttribute("currentUser");
        return "redirect:/";
    }
}