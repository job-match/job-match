package com.project.jobmatch.services.interfaces;

import com.project.jobmatch.models.Requirement;

import java.util.Set;

public interface RequirementService {
    Requirement createRequirement(String requirementName);

    Requirement getRequirementByName(String name);

    Set<Requirement> findRequirementsByType(Set<String> requirementsTypes);
}
