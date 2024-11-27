package com.project.jobmatch.repositories.interfaces;

import com.project.jobmatch.models.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Integer> {
    Optional<Skill> findSkillByType(String type);
}
