package com.project.jobmatch.services.interfaces;

import com.project.jobmatch.models.Company;

import java.util.List;

public interface CompanyService {
    Company getByUsername(String username);
    Company getCompanyById(int id);
    List<Company> getAllCompanies();
}
