package com.project.jobmatch.services;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.EntityDuplicateException;
import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.models.Company;
import com.project.jobmatch.repositories.interfaces.CompanyRepository;
import com.project.jobmatch.services.interfaces.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    public static final String COMPANY = "Company";
    private static final String MODIFY_PROFILE_ERROR_MESSAGE = "Only company's account can make changes to the company.";

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company getCompanyByUsername(String username) {
        return companyRepository
                .getCompanyByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(COMPANY, username, "username"));
    }

    @Override
    public Company getCompanyById(int id) {
        return companyRepository
                .getCompanyById(id)
                .orElseThrow(()-> new EntityNotFoundException(COMPANY, id));
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public void registerCompany(Company company) {
        boolean existsDuplicate = true;

        try {
            getCompanyByUsername(company.getUsername());
        } catch (EntityNotFoundException e) {
            existsDuplicate = false;
        }

        if (existsDuplicate) {
            throw new EntityDuplicateException(COMPANY, "username", company.getUsername());
        } else {
            companyRepository.save(company);
        }
    }

    @Override
    public void updateCompany(Company companyAuthenticated, Company companyMapped) {
        checkModifyPermissions(companyAuthenticated, companyMapped);
        companyRepository.save(companyMapped);
    }

    @Override
    public void deleteCompany(Company companyToDelete, Company companyAuthenticated) {
        checkModifyPermissions(companyAuthenticated, companyToDelete);
        companyRepository.delete(companyToDelete);
    }

    private void checkModifyPermissions(Company companyAuthenticated, Company companyMapped) {
        if (!(companyAuthenticated.equals(companyMapped))) {
            throw new AuthorizationException(MODIFY_PROFILE_ERROR_MESSAGE);
        }
    }


}
