package com.project.jobmatch.controllers.rest.company;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.helpers.AuthenticationHelper;
import com.project.jobmatch.helpers.ModelMapper;
import com.project.jobmatch.models.Professional;
import com.project.jobmatch.models.dto.ProfessionalDtoOut;
import com.project.jobmatch.services.interfaces.CloudinaryService;
import com.project.jobmatch.services.interfaces.ProfessionalService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/company-portal/professionals")
public class ProfessionalForCompaniesRestController {

    private final ProfessionalService professionalService;
    private final ModelMapper modelMapper;
    private final AuthenticationHelper authenticationHelper;

    public ProfessionalForCompaniesRestController(ProfessionalService professionalService,
                                                      ModelMapper modelMapper,
                                                      AuthenticationHelper authenticationHelper) {
        this.professionalService = professionalService;
        this.modelMapper = modelMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping("/{id}")
    public ProfessionalDtoOut getProfessionalById(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            authenticationHelper.tryGetCompany(headers);
            Professional professional = professionalService.getProfessionalById(id);

            return modelMapper.fromProfessionalToProfessionalDtoOut(professional);

        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @GetMapping
    public List<ProfessionalDtoOut> getAllProfessionals(@RequestHeader HttpHeaders httpHeaders) {
        try{
            authenticationHelper.tryGetCompany(httpHeaders);
            List<Professional> professionals = professionalService.getAllProfessionals();

            return modelMapper.fromListProfessionalsToListProfessionalDtoOut(professionals);

        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    @GetMapping("/search")
    public List<ProfessionalDtoOut> searchProfessionals(@RequestHeader HttpHeaders httpHeaders,
                                                        @RequestParam(required = false) String username,
                                                        @RequestParam(required = false) String name,
                                                        @RequestParam(required = false) String email,
                                                        @RequestParam(required = false) String keyword,
                                                        @RequestParam(required = false) String location) {
        try {
            authenticationHelper.tryGetCompany(httpHeaders);

            List<Professional> professionalList = professionalService.searchProfessionals(
                    username,
                    name,
                    email,
                    keyword,
                    location);

            return modelMapper.fromListProfessionalsToListProfessionalDtoOut(professionalList);
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
