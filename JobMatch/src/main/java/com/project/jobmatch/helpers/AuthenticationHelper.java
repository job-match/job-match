package com.project.jobmatch.helpers;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.models.Company;
import com.project.jobmatch.models.Professional;
import com.project.jobmatch.services.interfaces.CompanyService;
import com.project.jobmatch.services.interfaces.ProfessionalService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationHelper {
    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";
    private static final String INVALID_AUTHENTICATION_ERROR = "Invalid authentication.";

    private final ProfessionalService professionalService;
    private final CompanyService companyService;

    @Autowired
    public AuthenticationHelper(ProfessionalService professionalService,
                                CompanyService companyService) {
        this.professionalService = professionalService;
        this.companyService = companyService;
    }

    public Professional tryGetProfessional(HttpHeaders headers) {
        if (!headers.containsKey(AUTHORIZATION_HEADER_NAME)) {
            throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }

        try {
            String professionalInfo = headers.getFirst(AUTHORIZATION_HEADER_NAME);
            String username = getUsername(professionalInfo);
            String password = getPassword(professionalInfo);
            Professional professional = professionalService.getByUsername(username);

            if (!professional.getPassword().equals(password)) {
                throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
            }

            return professional;

        } catch (EntityNotFoundException e) {
            throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }
    }

    public Company tryGetCompany(HttpHeaders headers) {
        if (!headers.containsKey(AUTHORIZATION_HEADER_NAME)) {
            throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }

        try {
            String companyInfo = headers.getFirst(AUTHORIZATION_HEADER_NAME);
            String username = getUsername(companyInfo);
            String password = getPassword(companyInfo);
            Company company = companyService.getCompanyByUsername(username);

            if (!company.getPassword().equals(password)) {
                throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
            }

            return company;

        } catch (EntityNotFoundException e) {
            throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }
    }

    private String getUsername(String info) {
        int firstSpace = info.indexOf(" ");
        if (firstSpace == -1) {
            throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }

        return info.substring(0, firstSpace);
    }

    private String getPassword(String info) {
        int firstSpace = info.indexOf(" ");
        if (firstSpace == -1) {
            throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }

        return info.substring(firstSpace + 1);
    }

    public Professional tryGetCurrentProfessional(HttpSession session) {
        String currentUsername = session.getAttribute("currentUser").toString();

        if (currentUsername == null) {
            throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }

        return professionalService.getByUsername(currentUsername);
    }

    public Company tryGetCurrentCompany(HttpSession session) {
        String currentUsername = session.getAttribute("currentUser").toString();

        if (currentUsername == null) {
            throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }

        return companyService.getCompanyByUsername(currentUsername);
    }

    public Professional verifyAuthenticationProfessional(String username, String password) {
        try {
            Professional professional = professionalService.getByUsername(username);
            if (!professional.getPassword().equals(password)) {
                throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
            }
            return professional;
        } catch (EntityNotFoundException e) {
            throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }
    }

    public Company verifyAuthenticationCompany(String username, String password) {
        try {
            Company company = companyService.getCompanyByUsername(username);
            if (!company.getPassword().equals(password)) {
                throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
            }
            return company;
        } catch (EntityNotFoundException e) {
            throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }
    }
}
