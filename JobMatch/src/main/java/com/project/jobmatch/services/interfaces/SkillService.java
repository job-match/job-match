package com.project.jobmatch.services.interfaces;

import com.project.jobmatch.models.Skill;

public interface SkillService {
    Skill createSkill(String skillName);
    Skill getSkillByName(String name);
}
