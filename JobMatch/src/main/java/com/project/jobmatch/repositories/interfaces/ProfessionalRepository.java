package com.project.jobmatch.repositories.interfaces;

import com.project.jobmatch.models.Professional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessionalRepository extends JpaRepository<Professional, Integer> {

    Professional findProfessionalByUsername(String username);
}
