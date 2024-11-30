package com.project.jobmatch.services;

import com.project.jobmatch.models.Requirement;
import com.project.jobmatch.repositories.interfaces.RequirementRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.project.jobmatch.Helpers.createMockRequirement;

@ExtendWith(MockitoExtension.class)
public class RequirementServiceImplTests {

    @Mock
    private RequirementRepository requirementRepository;

    @InjectMocks
    private RequirementServiceImpl mockRequirementServiceImpl;

    @Test
    public void createRequirement_Should_ReturnExistingRequirement_When_RequirementExists() {
        // Arrange
        String requirementName = "Java Programming";
        Requirement mockRequirement = createMockRequirement();
        mockRequirement.setType(requirementName);

        Mockito.when(requirementRepository.findRequirementByType(requirementName))
                .thenReturn(Optional.of(mockRequirement));

        // Act
        Requirement result = mockRequirementServiceImpl.createRequirement(requirementName);

        // Assert
        Assertions.assertEquals(mockRequirement, result);
        Mockito.verify(requirementRepository, Mockito.never()).save(Mockito.any(Requirement.class));
    }

    @Test
    public void createRequirement_Should_CreateAndSaveNewRequirement_When_RequirementDoesNotExist() {
        // Arrange
        String requirementName = "Python Programming";
        Requirement mockRequirement = new Requirement();
        mockRequirement.setType(requirementName);

        Mockito.when(requirementRepository.findRequirementByType(requirementName))
                .thenReturn(Optional.empty());
        Mockito.when(requirementRepository.save(Mockito.any(Requirement.class)))
                .thenReturn(mockRequirement);

        // Act
        Requirement result = mockRequirementServiceImpl.createRequirement(requirementName);

        // Assert
        Assertions.assertEquals(mockRequirement, result);
        Mockito.verify(requirementRepository, Mockito.times(1)).save(Mockito.any(Requirement.class));
    }
}
