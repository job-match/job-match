package com.project.jobmatch.repositories.interfaces;

import com.project.jobmatch.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    Company getCompanyByUsername(String username);

    Company getCompanyById(int id);

    @Override
    List<Company> findAll();
}
