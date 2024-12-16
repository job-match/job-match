package com.project.jobmatch.services;

import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.models.Requirement;
import com.project.jobmatch.repositories.interfaces.RequirementRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.project.jobmatch.Helpers.createMockRequirement;

@ExtendWith(MockitoExtension.class)
public class RequirementServiceImplTests {

    @Mock
    private RequirementRepository mockRequirementRepository;

    @InjectMocks
    private RequirementServiceImpl mockRequirementServiceImpl;

    @Test
    public void createRequirement_Should_ReturnExistingRequirement_When_RequirementExists() {
        // Arrange
        String requirementName = "Java Programming";
        Requirement mockRequirement = createMockRequirement();
        mockRequirement.setType(requirementName);

        Mockito.when(mockRequirementRepository.findRequirementByType(requirementName))
                .thenReturn(Optional.of(mockRequirement));

        // Act
        Requirement result = mockRequirementServiceImpl.createRequirement(requirementName);

        // Assert
        Assertions.assertEquals(mockRequirement, result);
        Mockito.verify(mockRequirementRepository, Mockito.never()).save(Mockito.any(Requirement.class));
    }

    @Test
    public void createRequirement_Should_CreateAndSaveNewRequirement_When_RequirementDoesNotExist() {
        // Arrange
        String requirementName = "Python Programming";
        Requirement mockRequirement = new Requirement();
        mockRequirement.setType(requirementName);

        Mockito.when(mockRequirementRepository.findRequirementByType(requirementName))
                .thenReturn(Optional.empty());
        Mockito.when(mockRequirementRepository.save(Mockito.any(Requirement.class)))
                .thenReturn(mockRequirement);

        // Act
        Requirement result = mockRequirementServiceImpl.createRequirement(requirementName);

        // Assert
        Assertions.assertEquals(mockRequirement, result);
        Mockito.verify(mockRequirementRepository, Mockito.times(1)).save(Mockito.any(Requirement.class));
    }

    @Test
    public void getRequirementByName_Should_ReturnRequirement_When_RequirementExists() {
        // Arrange
        String requirementName = "ReactJS";
        Requirement mockRequirement = new Requirement();
        mockRequirement.setType(requirementName);

        Mockito.when(mockRequirementRepository.findRequirementByType(requirementName))
                .thenReturn(Optional.of(mockRequirement));

        // Act
        Requirement result = mockRequirementServiceImpl.getRequirementByName(requirementName);

        // Assert
        Assertions.assertEquals(mockRequirement, result);
        Mockito.verify(mockRequirementRepository, Mockito.times(1)).findRequirementByType(requirementName);
    }

    @Test
    public void getRequirementByName_Should_ThrowException_When_RequirementDoesNotExist() {
        // Arrange
        String requirementName = "Spring Boot";

        Mockito.when(mockRequirementRepository.findRequirementByType(requirementName))
                .thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () ->
                mockRequirementServiceImpl.getRequirementByName(requirementName));

        Assertions.assertTrue(exception.getMessage().contains("Requirement"));
        Assertions.assertTrue(exception.getMessage().contains("name"));
        Assertions.assertTrue(exception.getMessage().contains(requirementName));
        Mockito.verify(mockRequirementRepository, Mockito.times(1)).findRequirementByType(requirementName);
    }

    @Test
    public void findRequirementsByType_Should_ReturnEmptySet_When_NoRequirementTypesProvided() {
        // Arrange
        Set<String> requirementTypes = new HashSet<>();

        // Act
        Set<Requirement> result = mockRequirementServiceImpl.findRequirementsByType(requirementTypes);

        // Assert
        Assertions.assertTrue(result.isEmpty());
        Mockito.verifyNoInteractions(mockRequirementRepository);
    }


    @Test
    public void findRequirementsByType_Should_ReturnExistingRequirements_When_RequirementTypesAreFound() {
        // Arrange
        Set<String> requirementTypes = new HashSet<>();
        requirementTypes.add("Java");
        requirementTypes.add("MariaDB");

        Requirement javaRequirement = createMockRequirement();
        Requirement mariaRequirement = createMockRequirement();

        Mockito.when(mockRequirementRepository.findRequirementByType("Java"))
                .thenReturn(Optional.of(javaRequirement));
        Mockito.when(mockRequirementRepository.findRequirementByType("MariaDB"))
                .thenReturn(Optional.of(mariaRequirement));

        // Act
        Set<Requirement> result = mockRequirementServiceImpl.findRequirementsByType(requirementTypes);

        // Assert
        Assertions.assertTrue(result.contains(javaRequirement));
        Assertions.assertTrue(result.contains(mariaRequirement));
        Mockito.verify(mockRequirementRepository).findRequirementByType("Java");
        Mockito.verify(mockRequirementRepository).findRequirementByType("MariaDB");
    }


}
