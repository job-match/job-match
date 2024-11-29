package com.project.jobmatch.services.interfaces;

import com.project.jobmatch.models.Requirement;

public interface RequirementService {
    Requirement createRequirement(String requirementName);

    Requirement getRequirementByName(String name);
}
