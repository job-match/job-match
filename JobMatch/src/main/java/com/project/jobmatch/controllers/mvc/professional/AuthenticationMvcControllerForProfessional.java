package com.project.jobmatch.controllers.mvc.professional;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.helpers.AuthenticationHelper;
import com.project.jobmatch.helpers.ModelMapper;
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

    @GetMapping("/professional/login")
    public String showLoginPage(Model model) {
        model.addAttribute("loginProfessional", new ProfessionalLoginDto());
        return "professional/login-professional";
    }

    @PostMapping("/professional/login")
    public String handleLogin(@Valid @ModelAttribute("loginProfessional") ProfessionalLoginDto professionalLoginDto,
                              BindingResult bindingResult,
                              HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "professional/login-professional";
        }

        try {
            authenticationHelper.verifyAuthenticationProfessional(professionalLoginDto.getUsername(), professionalLoginDto.getPassword());
            session.setAttribute("currentUser", professionalLoginDto.getUsername());
            return "redirect:/";
        } catch (AuthorizationException e) {
            bindingResult.rejectValue("username", "auth_error", e.getMessage());
            return "professional/login-professional";
        }
    }

    @GetMapping("/professional/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("registerProfessional", new ProfessionalRegisterDto());
        return "professional/login-professional";
    }

//    @PostMapping("professional//register")
//    public String handleRegister(@Valid @ModelAttribute("register") ProfessionalRegisterDto professionalRegisterDto,
//                                 BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "professional/login-professional";
//        }
//
//        if (!professionalRegisterDto.getPassword().equals(professionalRegisterDto.getPasswordConfirmation())) {
//            bindingResult.rejectValue("passwordConfirm", "password_error", PASSWORD_CONFIRMATION_ERROR_MESSAGE);
//            return "professional/login-professional";
//        }
//
//        try {
//            Professional professional = modelMapper.fromProfessionalDtoInToProfessional(professionalRegisterDto);
//            userService.register(user);
//            return "redirect:/auth/login";
//        } catch (EntityDuplicateException e) {
//            bindingResult.rejectValue("username", "username_error", e.getMessage());
//            return "professional/login-professional";
//        }
//    }
}
