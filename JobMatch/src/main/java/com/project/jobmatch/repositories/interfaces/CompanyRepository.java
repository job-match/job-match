package com.project.jobmatch.repositories.interfaces;

import com.project.jobmatch.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    Company findCompanyByUsername(String username);
}
