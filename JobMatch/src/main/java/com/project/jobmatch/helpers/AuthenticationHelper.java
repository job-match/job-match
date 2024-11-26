package com.project.jobmatch.helpers;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.models.Company;
import com.project.jobmatch.models.Professional;
import com.project.jobmatch.services.interfaces.CompanyService;
import com.project.jobmatch.services.interfaces.ProfessionalService;
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

    private String getUsername(String userInfo) {
        int firstSpace = userInfo.indexOf(" ");
        if (firstSpace == -1) {
            throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }

        return userInfo.substring(0, firstSpace);
    }

    private String getPassword(String userInfo) {
        int firstSpace = userInfo.indexOf(" ");
        if (firstSpace == -1) {
            throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
        }

        return userInfo.substring(firstSpace + 1);
    }

//    public User tryGetCurrentUser(HttpSession session) {
//        String currentUsername = session.getAttribute("currentUser").toString();
//
//        if (currentUsername == null) {
//            throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
//        }
//
//        return userService.getByUsername(currentUsername);
//    }
//
//    public User verifyAuthentication(String username, String password) {
//        try {
//            User user = userService.getByUsername(username);
//            if (!user.getPassword().equals(password)) {
//                throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
//            }
//            return user;
//        } catch (EntityNotFoundException e) {
//            throw new AuthorizationException(INVALID_AUTHENTICATION_ERROR);
//        }
//    }
}
