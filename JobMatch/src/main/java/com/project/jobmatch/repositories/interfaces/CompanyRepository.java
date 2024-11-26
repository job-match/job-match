package com.project.jobmatch.repositories.interfaces;

import com.project.jobmatch.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    Optional<Company> getCompanyByUsername(String username);

    Optional<Company> getCompanyById(int id);

    @Override
    List<Company> findAll();


}
