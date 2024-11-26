package com.project.jobmatch.services.interfaces;

import com.project.jobmatch.models.Company;

public interface CompanyService {
    Company getByUsername(String username);
}
