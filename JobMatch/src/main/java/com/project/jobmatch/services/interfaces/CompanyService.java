package com.project.jobmatch.services.interfaces;

import com.project.jobmatch.models.Company;

import java.util.List;

public interface CompanyService {
    Company getCompanyByUsername(String username);
    Company getCompanyById(int id);
    List<Company> getAllCompanies();
    void registerCompany(Company company);
    void updateCompany(Company companyAuthenticated, Company companyMapped);
    void deleteCompany(Company companyToDelete, Company companyAuthenticated);
}
