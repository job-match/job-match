package com.project.jobmatch.services.interfaces;

import com.project.jobmatch.models.Professional;

public interface ProfessionalService {
    Professional getByUsername(String username);
}
