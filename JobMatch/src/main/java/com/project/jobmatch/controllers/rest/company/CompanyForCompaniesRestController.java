package com.project.jobmatch.controllers.rest.company;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.EntityDuplicateException;
import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.helpers.AuthenticationHelper;
import com.project.jobmatch.helpers.ModelMapper;
import com.project.jobmatch.models.Company;
import com.project.jobmatch.models.dto.CompanyDtoInCreate;
import com.project.jobmatch.models.dto.CompanyDtoInUpdate;
import com.project.jobmatch.models.dto.CompanyDtoOut;
import com.project.jobmatch.services.CloudinaryImage;
import com.project.jobmatch.services.interfaces.CloudinaryService;
import com.project.jobmatch.services.interfaces.CompanyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/company-portal/companies")
public class CompanyForCompaniesRestController {
    public static final String UPLOAD_COMPANY_PICTURE_ERROR_MESSAGE = "Could not upload company's picture!";


    private final AuthenticationHelper authenticationHelper;
    private final ModelMapper modelMapper;
    private final CompanyService companyService;
    private final CloudinaryService cloudinaryService;

    public CompanyForCompaniesRestController(CompanyService companyService,
                                             AuthenticationHelper authenticationHelper,
                                             ModelMapper modelMapper,
                                             CloudinaryService cloudinaryService) {
        this.companyService = companyService;
        this.authenticationHelper = authenticationHelper;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping
    public List<CompanyDtoOut> getAllCompanies(@RequestHeader HttpHeaders httpHeaders) {
        try {
            authenticationHelper.tryGetCompany(httpHeaders);
            List<Company> companyList = companyService.getAllCompanies();

            return modelMapper.fromListCompaniesToListCompaniesDtoOut(companyList);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public CompanyDtoOut getCompanyById(@RequestHeader HttpHeaders httpHeaders, @PathVariable int id) {
        try {
            authenticationHelper.tryGetCompany(httpHeaders);
            Company company = companyService.getCompanyById(id);

            return modelMapper.fromCompanyToCompanyDtoOut(company);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @PostMapping()
    public CompanyDtoOut registerCompany(@Valid @RequestBody CompanyDtoInCreate companyDtoInCreate) {
        try {
            Company company = modelMapper.fromCompanyDtoInCreateToCompany(companyDtoInCreate);
            companyService.registerCompany(company);

            return modelMapper.fromCompanyToCompanyDtoOut(company);
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PostMapping("/{id}/picture")
    public void uploadPictureOfCompany(@RequestHeader HttpHeaders headers,
                                   @PathVariable int id,
                                   @RequestPart("picture") MultipartFile picture) {
        try {
            Company companyAuthenticated = authenticationHelper.tryGetCompany(headers);
            Company companyToUploadPicture = companyService.getCompanyById(id);
            CloudinaryImage cloudinaryImage = cloudinaryService.upload(picture);

            companyService.uploadPictureToCompany(companyAuthenticated, companyToUploadPicture, cloudinaryImage);

        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, UPLOAD_COMPANY_PICTURE_ERROR_MESSAGE);
        }
    }

    @PutMapping("/{id}")
    public CompanyDtoOut updateCompany(@RequestHeader HttpHeaders httpHeaders,
                                       @PathVariable int id,
                                       @Valid @RequestBody CompanyDtoInUpdate companyDtoInUpdate) {
        try {
            Company companyAuthenticated = authenticationHelper.tryGetCompany(httpHeaders);
            Company companyMapped = modelMapper.fromCompanyDtoInUpdateToCompany(id, companyDtoInUpdate);
            companyService.updateCompany(companyAuthenticated, companyMapped);

            return modelMapper.fromCompanyToCompanyDtoOut(companyMapped);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCompany(@RequestHeader HttpHeaders httpHeaders, @PathVariable int id) {
        try {
            Company companyAuthenticated = authenticationHelper.tryGetCompany(httpHeaders);
            Company companyToDelete = companyService.getCompanyById(id);

            companyService.deleteCompany(companyToDelete, companyAuthenticated);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    //TODO Method to match to job application

}
