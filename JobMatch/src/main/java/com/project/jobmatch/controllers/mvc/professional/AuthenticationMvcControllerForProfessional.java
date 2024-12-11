package com.project.jobmatch.controllers.mvc.professional;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.EntityDuplicateException;
import com.project.jobmatch.helpers.AuthenticationHelper;
import com.project.jobmatch.helpers.ModelMapper;
import com.project.jobmatch.models.Professional;
import com.project.jobmatch.models.dto.ProfessionalLoginDto;
import com.project.jobmatch.models.dto.ProfessionalRegisterDto;
import com.project.jobmatch.services.interfaces.ProfessionalService;
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
public class AuthenticationMvcControllerForProfessional {
    private final ProfessionalService professionalService;
    private final AuthenticationHelper authenticationHelper;
    private final ModelMapper modelMapper;

    public AuthenticationMvcControllerForProfessional(ProfessionalService professionalService, AuthenticationHelper authenticationHelper, ModelMapper modelMapper) {
        this.professionalService = professionalService;
        this.authenticationHelper = authenticationHelper;
        this.modelMapper = modelMapper;
    }

    @ModelAttribute("isAuthenticated")
    public boolean populateIsAuthenticated(HttpSession session) {
        return session.getAttribute("currentUser") != null;
    }

    @GetMapping("/professional-portal/login")
    public String showLoginPage(Model model) {
        if (!model.containsAttribute("loginProfessional")) {
            model.addAttribute("loginProfessional", new ProfessionalLoginDto());
        }
        if (!model.containsAttribute("registerProfessional")) {
            model.addAttribute("registerProfessional", new ProfessionalRegisterDto());
        }
        model.addAttribute("activeTab", "login");
        return "professional/login-professional";
    }

    @PostMapping("/professional-portal/login")
    public String handleLogin(@Valid @ModelAttribute("loginProfessional") ProfessionalLoginDto professionalLoginDto,
                              BindingResult bindingResult,
                              HttpSession session,
                              Model model) {
        if (bindingResult.hasErrors()) {
            if (!model.containsAttribute("registerProfessional")) {
                model.addAttribute("registerProfessional", new ProfessionalRegisterDto());
            }
            return "professional/login-professional";
        }

        try {
            authenticationHelper.verifyAuthenticationProfessional(professionalLoginDto.getUsername(), professionalLoginDto.getPassword());
            session.setAttribute("currentUser", professionalLoginDto.getUsername());
            return "redirect:/";
        } catch (AuthorizationException e) {
            bindingResult.rejectValue("username", "auth_error", e.getMessage());
            if (!model.containsAttribute("registerProfessional")) {
                model.addAttribute("registerProfessional", new ProfessionalRegisterDto());
            }
            return "professional/login-professional";
        }
    }

    @GetMapping("/professional-portal/register")
    public String showRegisterPage(Model model) {
        if (!model.containsAttribute("loginProfessional")) {
            model.addAttribute("loginProfessional", new ProfessionalLoginDto());
        }
        if (!model.containsAttribute("registerProfessional")) {
            model.addAttribute("registerProfessional", new ProfessionalRegisterDto());
        }
        return "professional/login-professional";
    }

    @PostMapping("/professional-portal/register")
    public String handleRegister(@Valid @ModelAttribute("registerProfessional") ProfessionalRegisterDto professionalRegisterDto,
                                 BindingResult bindingResult,
                                 Model model) {
        if (bindingResult.hasErrors()) {
            if (!model.containsAttribute("loginProfessional")) {
                model.addAttribute("loginProfessional", new ProfessionalLoginDto());
            }
            model.addAttribute("activeTab", "register");
            return "professional/login-professional";
        }

        if (!professionalRegisterDto.getPassword().equals(professionalRegisterDto.getPasswordConfirmation())) {
            bindingResult.rejectValue("passwordConfirmation", "password_error", PASSWORD_CONFIRMATION_ERROR_MESSAGE);
            if (!model.containsAttribute("loginProfessional")) {
                model.addAttribute("loginProfessional", new ProfessionalLoginDto());
            }
            model.addAttribute("activeTab", "register");
            return "professional/login-professional";
        }

        try {
            Professional professional = modelMapper.fromProfessionalRegisterDto(professionalRegisterDto);
            professionalService.registerProfessional(professional);
            return "redirect:/auth/professional-portal/login";
        } catch (EntityDuplicateException e) {
            bindingResult.rejectValue("username", "username_error", e.getMessage());
            if (!model.containsAttribute("loginProfessional")) {
                model.addAttribute("loginProfessional", new ProfessionalLoginDto());
            }
            model.addAttribute("activeTab", "register");
            return "professional/login-professional";
        }
    }
}