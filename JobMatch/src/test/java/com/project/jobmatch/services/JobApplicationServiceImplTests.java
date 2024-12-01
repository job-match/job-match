package com.project.jobmatch.services;

import com.project.jobmatch.Helpers;
import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.exceptions.MatchRequestDeniedException;
import com.project.jobmatch.models.*;
import com.project.jobmatch.repositories.interfaces.JobApplicationRepository;
import com.project.jobmatch.services.interfaces.MatchService;
import com.project.jobmatch.services.interfaces.ProfessionalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import static com.project.jobmatch.helpers.RestControllersConstants.JOB_APP_STATUS_TO_ACCEPT;
import static com.project.jobmatch.helpers.RestControllersConstants.JOB_APP_STATUS_TO_IGNORE;
import static com.project.jobmatch.helpers.ServicesConstants.PROFESSIONAL_STATUS_BUSY;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JobApplicationServiceImplTests {
    @Mock
    private JobApplicationRepository mockJobApplicationRepository;

    @Mock
    private ProfessionalService mockProfessionalService;

    @Mock
    private MatchService mockMatchService;

    @InjectMocks
    JobApplicationServiceImpl mockJobApplicationService;

    @Test
    void getAllJobApplications_Should_CallRepository() {
        // Arrange
        when(mockJobApplicationRepository.findAll())
                .thenReturn(List.of());

        // Act
        mockJobApplicationService.getAllJobApplications();

        // Assert
        verify(mockJobApplicationRepository, times(1)).
                findAll();
    }

    @Test
    void createJobApplication_Should_CallRepository() {
        // Arrange
        JobApplication mockJobApplication = Helpers.createMockApplication();
        Professional mockAuthenticatedProfessional = Helpers.createMockProfessional();
        mockAuthenticatedProfessional.setStatus(Helpers.createMockStatus());

        // Act
        mockJobApplicationService.createJobApplication(mockJobApplication, mockAuthenticatedProfessional);

        // Assert
        verify(mockJobApplicationRepository, times(1)).
                save(mockJobApplication);
    }

    @Test
    void createJobApplication_Should_Throw_When_Unauthorized() {
        // Arrange
        JobApplication mockJobApplication = Helpers.createMockApplication();
        Professional mockAuthenticatedProfessional = Helpers.createMockProfessional();
        mockAuthenticatedProfessional.getStatus().setType(PROFESSIONAL_STATUS_BUSY);

        // Act, Assert
        assertThrows(AuthorizationException.class, () ->
                mockJobApplicationService.createJobApplication(mockJobApplication, mockAuthenticatedProfessional)
        );
    }

    @Test
    void updateJobApplication_Should_CallRepository() {
        // Arrange
        JobApplication mockJobApplication = Helpers.createMockApplication();
        Professional mockAuthenticatedProfessional = Helpers.createMockProfessional();

        // Act
        mockJobApplicationService.updateJobApplication(mockAuthenticatedProfessional, mockJobApplication);

        // Assert
        verify(mockJobApplicationRepository, times(1)).
                save(mockJobApplication);
    }

    @Test
    void updateJobApplication_Should_Throw_When_Unauthorized() {
        // Arrange
        JobApplication mockJobApplication = Helpers.createMockApplication();
        Professional mockAuthenticatedProfessional = Helpers.createMockProfessional();
        mockAuthenticatedProfessional.setId(2);

        // Act, Assert
        assertThrows(AuthorizationException.class, () ->
                mockJobApplicationService.updateJobApplication(mockAuthenticatedProfessional, mockJobApplication)
        );
    }

    @Test
    void deleteJobApplication_Should_CallRepository() {
        // Arrange
        JobApplication mockJobApplication = Helpers.createMockApplication();
        Professional mockAuthenticatedProfessional = Helpers.createMockProfessional();
        when(mockProfessionalService.getProfessionalByJobApplicationId(mockJobApplication.getId()))
                .thenReturn(mockAuthenticatedProfessional);

        // Act
        mockJobApplicationService.deleteJobApplication(mockJobApplication, mockAuthenticatedProfessional);

        // Assert
        verify(mockJobApplicationRepository).delete(mockJobApplication);
    }

    @Test
    void deleteJobApplication_Should_Throw_When_Unauthorized() {
        // Arrange
        JobApplication mockJobApplication = Helpers.createMockApplication();
        Professional mockAuthenticatedProfessional = Helpers.createMockProfessional();

        when(mockProfessionalService.getProfessionalByJobApplicationId(mockJobApplication.getId()))
                .thenReturn(new Professional());

        //Assert
        assertThrows(AuthorizationException.class, () ->
                mockJobApplicationService.deleteJobApplication(mockJobApplication, mockAuthenticatedProfessional)
        );
    }

    @Test
    void getAllJobApplicationsOfProfessional_Should_ReturnListOfJobApplications() {
        // Arrange
        Professional mockProfessional = Helpers.createMockProfessional();
        Professional mockAuthenticatedProfessional = Helpers.createMockProfessional();
        List<JobApplication> mockApplications = List.of(Helpers.createMockApplication());

        when(mockJobApplicationRepository.findJobApplicationsByProfessionalId(mockProfessional.getId()))
                .thenReturn(mockApplications);

        // Act
        List<JobApplication> result = mockJobApplicationService.getAllJobApplicationsOfProfessional(
                mockProfessional, mockAuthenticatedProfessional
        );

        // Assert
        assertEquals(mockApplications, result);
        verify(mockJobApplicationRepository).findJobApplicationsByProfessionalId(mockProfessional.getId());
    }

    @Test
    void getAllJobApplicationsOfProfessional_Should_Throw_When_Unauthorized() {
        // Arrange
        Professional mockProfessional = Helpers.createMockProfessional();
        Professional mockAuthenticatedProfessional = Helpers.createMockProfessional();
        mockAuthenticatedProfessional.setId(2);

        // Act, Assert
        assertThrows(AuthorizationException.class, () ->
                mockJobApplicationService.getAllJobApplicationsOfProfessional(
                        mockProfessional, mockAuthenticatedProfessional
                )
        );
    }

    @Test
    void getAllActiveJobApplications_Should_ReturnListOfAllActiveJobApplications() {
        // Arrange
        List<JobApplication> mockApplications = List.of(Helpers.createMockApplication());

        when(mockJobApplicationRepository.findJobApplicationsByStatus(JOB_APP_STATUS_TO_ACCEPT))
                .thenReturn(mockApplications);

        // Act
        List<JobApplication> result = mockJobApplicationService.getAllActiveJobApplications();

        // Assert
        assertEquals(mockApplications, result);
        verify(mockJobApplicationRepository).findJobApplicationsByStatus(JOB_APP_STATUS_TO_ACCEPT);
    }

    @Test
    void searchJobApplications_Should_ReturnListOfFilteredJobApplications() {
        // Arrange
        String location = "MockLocationName";
        Double minSalary = 2000.0;
        Double maxSalary = 3000.0;
        String skill = "Java";
        String keyword = "Developer";
        List<JobApplication> mockApplications = List.of(Helpers.createMockApplication());

        when(mockJobApplicationService.searchJobApplications(location, minSalary, maxSalary, skill, keyword))
                .thenReturn(mockApplications);

        // Act
        List<JobApplication> result = mockJobApplicationService.searchJobApplications(location, minSalary, maxSalary, skill, keyword);

        // Assert
        assertEquals(mockApplications, result);
        verify(mockJobApplicationRepository).searchJobApplications(location, minSalary, maxSalary, skill, keyword);
    }

    @Test
    void addJobAdToListOfAdMatchRequests_Should_CallCreateMatch_When_MatchExists() {
        // Arrange
        JobApplication mockJobApplication = Helpers.createMockApplication();
        JobAd mockJobAd = Helpers.createMockJobAd();

        mockJobAd.getListOfApplicationMatchRequests().add(mockJobApplication);

        // Act
        mockJobApplicationService.addJobAdToListOfAdMatchRequests(mockJobApplication, mockJobAd);

        // Assert
        verify(mockMatchService, times(1)).createMatch(mockJobAd, mockJobApplication);
        verify(mockJobApplicationRepository, Mockito.never()).save(mockJobApplication);
    }

    @Test
    void addJobAdToListOfAdMatchRequests_Should_Throw_When_RequestDenied() {
        // Arrange
        JobApplication mockJobApplication = Helpers.createMockApplication();
        JobAd mockJobAd = Helpers.createMockJobAd();

        mockJobApplication.getListOfAdMatchRequests().add(mockJobAd);

        // Act & Assert
        assertThrows(MatchRequestDeniedException.class, () ->
                mockJobApplicationService.addJobAdToListOfAdMatchRequests(mockJobApplication, mockJobAd)
        );

        verify(mockJobApplicationRepository, Mockito.never()).save(mockJobApplication);
    }

    @Test
    void checkSalaryMatch_Should_ReturnTrue_When_SalariesMatch() {
        // Act
        boolean result = mockJobApplicationService.checkSalaryMatch(2000.0, 3000.0, 1800.0, 3200.0);

        // Assert
        assertTrue(result);
    }

    @Test
    void checkSalaryMatch_Should_ReturnFalse_When_SalariesDoNotMatch() {
        // Act
        boolean result = mockJobApplicationService.checkSalaryMatch(2000.0, 3000.0, 1500.0, 1900.0);

        // Assert
        assertFalse(result);
    }

    @Test
    void checkSkillsAndRequirements_Should_ReturnFalse_When_RequirementsNotMet() {
        // Arrange
        Set<Skill> mockSkills = new HashSet<>();

        Requirement mockRequirement = Helpers.createMockRequirement();
        mockRequirement.setType("NotMetRequirement");
        Set<Requirement> mockRequirements = new HashSet<>();
        mockRequirements.add(mockRequirement);

        // Act
        boolean result = mockJobApplicationService.checkSkillsAndRequirements(mockSkills, mockRequirements);

        // Assert
        assertFalse(result);
    }

    @Test
    void checkLocations_Should_ReturnTrue_When_LocationIsRemote() {
        // Act
        boolean result = mockJobApplicationService.checkLocations("SomeLocation", "REMOTE");

        // Assert
        assertTrue(result);
    }

    @Test
    void checkLocations_Should_ReturnFalse_When_LocationIsDifferent() {
        // Act
        boolean result = mockJobApplicationService.checkLocations("CityA", "CityB");

        // Assert
        assertFalse(result);
    }

    @Test
    void checkLocations_Should_ReturnTrue_When_LocationIsHybrid() {
        // Act
        boolean result = mockJobApplicationService.checkLocations("CityA", "HYBRID");

        // Assert
        assertTrue(result);
    }

    @Test
    public void getJobApplicationByIdFromCompany_Should_ReturnJobApplication_When_Exist() {
        // Arrange
        int jobApplicationId = 1;
        JobApplication mockJobApplication = new JobApplication();
        mockJobApplication.setId(jobApplicationId);
        when(mockJobApplicationRepository.findJobApplicationByIdFromCompany(jobApplicationId, JOB_APP_STATUS_TO_IGNORE))
                .thenReturn(Optional.of(mockJobApplication));

        // Act
        JobApplication result = mockJobApplicationService.getJobApplicationByIdFromCompany(jobApplicationId);

        // Assert
        assertNotNull(result);
        assertEquals(jobApplicationId, result.getId());
        verify(mockJobApplicationRepository, times(1)).findJobApplicationByIdFromCompany(jobApplicationId, JOB_APP_STATUS_TO_IGNORE);
    }

    @Test
    public void getJobApplicationByIdFromCompany_Should_ThrowEntityNotFoundException_When_NotFound() {
        // Arrange
        int jobApplicationId = 1;
        when(mockJobApplicationRepository.findJobApplicationByIdFromCompany(jobApplicationId, JOB_APP_STATUS_TO_IGNORE))
                .thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            mockJobApplicationService.getJobApplicationByIdFromCompany(jobApplicationId);
        });

        assertEquals("Job application with id 1 not found.", exception.getMessage());
    }

    @Test
    public void getJobApplicationById_Should_ReturnJobApplication_When_Exist() {
        // Arrange
        int jobApplicationId = 1;
        JobApplication mockJobApplication = new JobApplication();
        mockJobApplication.setId(jobApplicationId);
        when(mockJobApplicationRepository.findJobApplicationById(jobApplicationId))
                .thenReturn(Optional.of(mockJobApplication));

        // Act
        JobApplication result = mockJobApplicationService.getJobApplicationById(jobApplicationId);

        // Assert
        assertNotNull(result);
        assertEquals(jobApplicationId, result.getId());
        verify(mockJobApplicationRepository, times(1)).findJobApplicationById(jobApplicationId);
    }

    @Test
    public void getJobApplicationById_Should_ThrowEntityNotFoundException_When_NotFound() {
        // Arrange
        int jobApplicationId = 1;
        when(mockJobApplicationRepository.findJobApplicationById(jobApplicationId))
                .thenReturn(Optional.empty());

        // Act & Assert
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            mockJobApplicationService.getJobApplicationById(jobApplicationId);
        });

        assertEquals("Job application with id 1 not found.", exception.getMessage());
    }
}
