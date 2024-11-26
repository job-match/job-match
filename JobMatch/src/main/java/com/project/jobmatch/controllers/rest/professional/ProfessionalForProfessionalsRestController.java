package com.project.jobmatch.controllers.rest.professional;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.helpers.AuthenticationHelper;
import com.project.jobmatch.helpers.ModelMapper;
import com.project.jobmatch.models.Professional;
import com.project.jobmatch.services.interfaces.CloudinaryService;
import com.project.jobmatch.services.interfaces.ProfessionalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public Professional getProfessionalById(@RequestHeader HttpHeaders headers, @PathVariable int id) {
        try {
            authenticationHelper.tryGetProfessional(headers);
            Professional professional = professionalService.getProfessionalById(id);

//            return modelMapper.fromUserToUserDtoOut(user);

            return professional;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

//    @PostMapping()
//    public Professional registerProfessional(@Valid @RequestBody UserDto userDto) {
//        try {
//            User user = modelMapper.fromUserDto(userDto);
//            userService.register(user);
//
//            return user;
//        } catch (EntityDuplicateException e) {
//            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
//        }
//    }


}
