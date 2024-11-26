package com.project.jobmatch.services;

import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.models.Professional;
import com.project.jobmatch.repositories.interfaces.ProfessionalRepository;
import com.project.jobmatch.services.interfaces.ProfessionalService;
import org.springframework.stereotype.Service;

@Service
public class ProfessionalServiceImpl implements ProfessionalService {

    private final ProfessionalRepository professionalRepository;

    public ProfessionalServiceImpl(ProfessionalRepository professionalRepository) {
        this.professionalRepository = professionalRepository;
    }

    @Override
    public Professional getByUsername(String username) {
        return professionalRepository
                .findProfessionalByUsername(username)
                .orElseThrow(()->new EntityNotFoundException("Professional", username, "username"));
    }

    @Override
    public Professional getProfessionalById(int id) {
        return professionalRepository
                .findProfessionalById(id)
                .orElseThrow(()-> new EntityNotFoundException("Professional", id));
    }
}
