package com.project.jobmatch.services;

import com.project.jobmatch.models.Company;
import com.project.jobmatch.repositories.interfaces.CompanyRepository;
import com.project.jobmatch.services.interfaces.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company getByUsername(String username) {
        return companyRepository.findCompanyByUsername(username);
    }
}
