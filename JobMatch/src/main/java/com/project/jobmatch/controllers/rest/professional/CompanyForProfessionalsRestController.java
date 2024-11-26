package com.project.jobmatch.controllers.rest.professional;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.helpers.AuthenticationHelper;
import com.project.jobmatch.helpers.ModelMapper;
import com.project.jobmatch.models.Company;
import com.project.jobmatch.models.dto.CompanyDtoOut;
import com.project.jobmatch.services.interfaces.CompanyService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/professional-portal/companies")
public class CompanyForProfessionalsRestController {

    private final AuthenticationHelper authenticationHelper;
    private final CompanyService companyService;
    private final ModelMapper modelMapper;

    public CompanyForProfessionalsRestController(AuthenticationHelper authenticationHelper, CompanyService companyService, ModelMapper modelMapper) {
        this.authenticationHelper = authenticationHelper;
        this.companyService = companyService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<CompanyDtoOut> getAllCompanies(@RequestHeader HttpHeaders httpHeaders) {
        try {
            authenticationHelper.tryGetProfessional(httpHeaders);
            List<Company> companyList = companyService.getAllCompanies();

            return modelMapper.fromListCompaniesToListCompaniesDtoOut(companyList);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public CompanyDtoOut getCompanyById(@RequestHeader HttpHeaders httpHeaders, @PathVariable int id) {
        try {
            authenticationHelper.tryGetProfessional(httpHeaders);
            Company company = companyService.getCompanyById(id);

            return modelMapper.fromCompanyToCompanyDtoOut(company);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
