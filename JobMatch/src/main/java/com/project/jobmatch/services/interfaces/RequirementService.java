package com.project.jobmatch.services.interfaces;

import com.project.jobmatch.models.Requirement;

public interface RequirementService {
    Requirement getRequirementByName(String name);
}
