package com.project.jobmatch.controllers.rest.company;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.helpers.AuthenticationHelper;
import com.project.jobmatch.models.Company;
import com.project.jobmatch.services.interfaces.CompanyService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/company-portal/companies")
public class CompanyForCompaniesRestController {

    private final AuthenticationHelper authenticationHelper;
    private final CompanyService companyService;

    public CompanyForCompaniesRestController(CompanyService companyService, AuthenticationHelper authenticationHelper) {
        this.companyService = companyService;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping
    public List<Company> getAllCompanies(@RequestHeader HttpHeaders httpHeaders) {
        try {
            authenticationHelper.tryGetCompany(httpHeaders);

            return companyService.getAllCompanies();
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Company getCompanyById(@RequestHeader HttpHeaders httpHeaders, @PathVariable int id) {
        try {
            authenticationHelper.tryGetCompany(httpHeaders);

            return companyService.getCompanyById(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }


}
