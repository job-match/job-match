package com.project.jobmatch.services;

import com.project.jobmatch.models.Skill;
import com.project.jobmatch.repositories.interfaces.SkillRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.project.jobmatch.Helpers.*;

public class SkillServiceImplTests {

    @Mock
    SkillRepository mockSkillRepository;

    @InjectMocks
    SkillServiceImpl mockSkillService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createSkill_Should_ReturnSkill_WhenValidStringIsPassed() {
        Skill mockSkill = createMockSkill();

        Mockito.when(mockSkillRepository.findSkillByType(Mockito.anyString()))
                .thenReturn(Optional.of(mockSkill));

        Skill result = mockSkillService.createSkill(Mockito.anyString());

        Assertions.assertEquals(result, mockSkill);
    }

    @Test
    public void createSkill_Should_CallRepositoryAdnSaveSkill_WhenValidStringIsPassed() {
        Skill mockSkill = createMockSkill();

        Mockito.when(mockSkillRepository.findSkillByType(mockSkill.getType()))
                .thenReturn(Optional.empty());

        Mockito.when(mockSkillRepository.save(Mockito.any(Skill.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Skill result = mockSkillService.createSkill(mockSkill.getType());

        // Assert
        Mockito.verify(mockSkillRepository).findSkillByType(mockSkill.getType());
        Mockito.verify(mockSkillRepository).save(Mockito.any(Skill.class));
        Assertions.assertEquals(mockSkill.getType(), result.getType());
    }

    @Test
    public void getSkillByName_Should_ReturnSkill_WhenValidStringIsPassed() {
        Skill mockSkill = createMockSkill();

        Mockito.when(mockSkillRepository.findSkillByType(Mockito.anyString()))
                .thenReturn(Optional.of(mockSkill));

        Skill result = mockSkillService.getSkillByName(Mockito.anyString());

        Assertions.assertEquals(result, mockSkill);
    }

    @Test
    public void findSkillsByType_Should_ReturnEmptySet_When_NoSkillTypesProvided() {
        // Arrange
        Set<String> skillTypes = new HashSet<>();

        // Act
        Set<Skill> result = mockSkillService.findSkillsByType(skillTypes);

        // Assert
        Assertions.assertTrue(result.isEmpty());
        Mockito.verifyNoInteractions(mockSkillRepository);
    }


}
