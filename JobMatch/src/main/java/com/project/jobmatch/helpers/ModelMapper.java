package com.project.jobmatch.helpers;

import com.project.jobmatch.services.interfaces.ProfessionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ModelMapper {
    private final ProfessionalService professionalService;

    @Autowired
    public ModelMapper(ProfessionalService profService) {
        this.professionalService = profService;
    }
}
