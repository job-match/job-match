package com.project.jobmatch.services.interfaces;

import com.project.jobmatch.models.Skill;

import java.util.Optional;
import java.util.Set;

public interface SkillService {
    Skill createSkill(String skillName);
    Skill getSkillByName(String name);
    Set<Skill> findSkillsByType(Set<String> skillsTypes);
}
