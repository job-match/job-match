package com.project.jobmatch.services;

import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.models.Skill;
import com.project.jobmatch.repositories.interfaces.SkillRepository;
import com.project.jobmatch.services.interfaces.SkillService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    public Skill createSkill(String skillName) {
        return skillRepository
                .findSkillByType(skillName)
                .orElseGet(() -> {
                    Skill skill = new Skill();
                    skill.setType(skillName);
                    return skillRepository.save(skill);
                });
    }

    @Override
    public Skill getSkillByName(String name) {
        return skillRepository
                .findSkillByType(name)
                .orElseThrow(() -> new EntityNotFoundException("Skill", "type", name));
    }

    @Override
    public Set<Skill> findSkillsByType(Set<String> skillsTypes) {
        Set<Skill> resultSkills = new HashSet<>();

        for (String skillType : skillsTypes) {
            Optional<Skill> skill = skillRepository.findSkillByType(skillType);

            if (skill.isPresent()) {
                Skill addSkill = skill.get();
                resultSkills.add(addSkill);
            } else {
                Skill newSkill = createSkill(skillType);
                resultSkills.add(newSkill);
            }
        }

        return resultSkills;
    }
}
