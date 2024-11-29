package com.project.jobmatch.services;

import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.models.Requirement;
import com.project.jobmatch.repositories.interfaces.RequirementRepository;
import com.project.jobmatch.services.interfaces.RequirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequirementServiceImpl implements RequirementService {

    private final RequirementRepository requirementRepository;

    @Autowired
    public RequirementServiceImpl(RequirementRepository requirementRepository) {
        this.requirementRepository = requirementRepository;
    }

    @Override
    public Requirement createRequirement(String requirementName) {
        return requirementRepository
                .findRequirementByType(requirementName)
                .orElseGet(() -> {
                    Requirement requirement = new Requirement();
                    requirement.setType(requirementName);
                    return requirementRepository.save(requirement);
                });
    }

    @Override
    public Requirement getRequirementByName(String name) {
        return requirementRepository
                .findRequirementByType(name)
                .orElseThrow(() -> new EntityNotFoundException("Requirement", "name", name));
    }
}
