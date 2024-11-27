package com.project.jobmatch.services.interfaces;

import com.project.jobmatch.models.Skill;

public interface SkillService {
    Skill getSkillByName(String name);
}
