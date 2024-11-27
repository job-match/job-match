package com.project.jobmatch.controllers.rest.professional;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.EntityDuplicateException;
import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.helpers.AuthenticationHelper;
import com.project.jobmatch.helpers.ModelMapper;
import com.project.jobmatch.models.Professional;
import com.project.jobmatch.models.dto.ProfessionalDtoInCreate;
import com.project.jobmatch.models.dto.ProfessionalDtoInUpdate;
import com.project.jobmatch.models.dto.ProfessionalDtoOut;
import com.project.jobmatch.services.interfaces.CloudinaryService;
import com.project.jobmatch.services.interfaces.ProfessionalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/professional-portal/professionals")
public class ProfessionalForProfessionalsRestController {

    private final ProfessionalService professionalService;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;
    private final AuthenticationHelper authenticationHelper;

    public ProfessionalForProfessionalsRestController(ProfessionalService professionalService,
                                                      CloudinaryService cloudinaryService,
                                                      ModelMapper modelMapper,
                                                      AuthenticationHelper authenticationHelper) {
        this.professionalService = professionalService;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @GetMapping("/{id}")
    public ProfessionalDtoOut getProfessionalById(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            authenticationHelper.tryGetProfessional(headers);
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
            authenticationHelper.tryGetProfessional(httpHeaders);
            List<Professional> professionals = professionalService.getAllProfessionals();

            return modelMapper.fromListProfessionalsToListProfessionalDtoOut(professionals);

        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }


    @PostMapping()
    public void registerProfessional(@Valid @RequestBody ProfessionalDtoInCreate professionalDtoInCreate) {
        try {
            Professional professional = modelMapper.fromProfessionalDtoInToProfessional(professionalDtoInCreate);
            professionalService.registerProfessional(professional);

        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public void updateProfessional(@RequestHeader HttpHeaders headers,
                              @PathVariable int id,
                              @Valid @RequestBody ProfessionalDtoInUpdate professionalDtoInUpdate) {
        try {
            Professional professionalAuthenticated = authenticationHelper.tryGetProfessional(headers);
            Professional professionalMapped = modelMapper.fromProfessionalDtoInToProfessional(id, professionalDtoInUpdate);

            professionalService.updateProfessional(professionalAuthenticated, professionalMapped);

        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }


}
