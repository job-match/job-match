package com.project.jobmatch.services;

import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.models.Requirement;
import com.project.jobmatch.models.Skill;
import com.project.jobmatch.repositories.interfaces.RequirementRepository;
import com.project.jobmatch.services.interfaces.RequirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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

    @Override
    public Set<Requirement> findRequirementsByType(Set<String> requirementsTypes) {
        Set<Requirement> resultRequirements = new HashSet<>();

        for (String requirementType : requirementsTypes) {
            Optional<Requirement> requirement = requirementRepository.findRequirementByType(requirementType);

            if (requirement.isPresent()) {
                Requirement addRequirement = requirement.get();
                resultRequirements.add(addRequirement);
            } else {
                Requirement newRequirement = createRequirement(requirementType);
                resultRequirements.add(newRequirement);
            }
        }

        return resultRequirements;
    }
}
