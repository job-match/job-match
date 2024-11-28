package com.project.jobmatch.repositories.interfaces;

import com.project.jobmatch.models.Professional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessionalRepository extends JpaRepository<Professional, Integer> {

    Optional<Professional> findProfessionalByUsername(String username);

    Optional<Professional> findProfessionalById(int id);

    @Query("SELECT p FROM Professional p JOIN p.jobApplications ja WHERE ja.id = :jobApplicationId")
    Optional<Professional> findProfessionalByJobApplicationId(@Param("jobApplicationId") int jobApplicationId);

    @Override
    List<Professional> findAll();

}
