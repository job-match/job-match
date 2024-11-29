package com.project.jobmatch.services;

import com.project.jobmatch.Helpers;
import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.models.JobApplication;
import com.project.jobmatch.models.Professional;
import com.project.jobmatch.repositories.interfaces.JobApplicationRepository;
import com.project.jobmatch.services.interfaces.ProfessionalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.project.jobmatch.helpers.RestControllersConstants.JOB_APP_STATUS_TO_ACCEPT;
import static com.project.jobmatch.helpers.ServicesConstants.PROFESSIONAL_STATUS_BUSY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JobApplicationServiceImplTests {
    @Mock
    private JobApplicationRepository mockJobApplicationRepository;

    @Mock
    private ProfessionalService mockProfessionalService;

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
        verify(mockJobApplicationRepository, Mockito.times(1)).
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
        verify(mockJobApplicationRepository, Mockito.times(1)).
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
        verify(mockJobApplicationRepository, Mockito.times(1)).
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

}
