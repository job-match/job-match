package com.project.jobmatch.repositories.interfaces;

import com.project.jobmatch.models.Requirement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RequirementRepository extends JpaRepository<Requirement, Integer> {
    Optional<Requirement> findRequirementByType(String type);
}
