package com.project.jobmatch.services;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.models.Company;
import com.project.jobmatch.models.JobAd;
import com.project.jobmatch.models.JobApplication;
import com.project.jobmatch.repositories.interfaces.JobAdRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static com.project.jobmatch.Helpers.*;

@ExtendWith(MockitoExtension.class)
public class JobAdServiceImplTests {

    @Mock
    JobAdRepository mockJobAdRepository;

    @InjectMocks
    JobAdServiceImpl mockJobAdService;

    @Test
    public void getJobAdById_Should_ReturnJobAd_When_IdExists() {
        // Arrange
        JobAd mockJobAd = createMockJobAd();

        Mockito.when(mockJobAdRepository.findJobAdById(Mockito.anyInt()))
                .thenReturn(Optional.of(mockJobAd));

        // Act
        JobAd jobAd = mockJobAdService.getJobAdById(Mockito.anyInt());

        // Assert
        Assertions.assertEquals(mockJobAd, jobAd);
    }

    @Test
    public void getJobAdById_Should_ThrowException_When_IdDoesNotExist() {
        //Act, Assert
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> mockJobAdService.getJobAdById(Mockito.anyInt()));
    }

    @Test
    public void getJobAdByTitle_Should_ReturnJobAd_When_TitleExists() {
        // Arrange
        JobAd mockJobAd = createMockJobAd();

        Mockito.when(mockJobAdRepository.findJobAdByPositionTitle(Mockito.anyString()))
                .thenReturn(Optional.of(mockJobAd));

        // Act
        JobAd jobAd = mockJobAdService.getJobAdByTitle(Mockito.anyString());

        // Assert
        Assertions.assertEquals(mockJobAd, jobAd);
    }

    @Test
    public void getJobAdByTitle_Should_ThrowException_When_TitleDoesNotExist() {
        //Act, Assert
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> mockJobAdService.getJobAdByTitle(Mockito.anyString()));
    }

    @Test
    public void getAll_Should_CallRepository() {
        //Arrange
        Mockito.when(mockJobAdRepository.findAll())
                .thenReturn(new ArrayList<JobAd>());

        //Act
        mockJobAdService.getAll();

        //Assert
        Mockito.verify(mockJobAdRepository, Mockito.times(1)).findAll();
    }

    @Test
    public void searchJobAds_Should_CallRepository() {
        //Arrange
        Mockito.when(mockJobAdRepository.searchJobAds(Mockito.anyString(), Mockito.anyString(),
                        Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyString()))
                .thenReturn(new ArrayList<JobAd>());

        //Act
        mockJobAdService.searchJobAds(Mockito.anyString(), Mockito.anyString(),
                Mockito.anyDouble(), Mockito.anyDouble(), Mockito.anyString());

        //Assert
        Mockito.verify(mockJobAdRepository, Mockito.times(1))
                .searchJobAds(Mockito.anyString(), Mockito.anyString(), Mockito.anyDouble(),
                        Mockito.anyDouble(), Mockito.anyString());
    }

    @Test
    public void createJobAd_Should_CallRepository() {
        //Arrange
        JobAd mockJobAd = createMockJobAd();

        //Act
        mockJobAdService.createJobAd(mockJobAd);

        //Assert
        Mockito.verify(mockJobAdRepository, Mockito.times(1)).save(mockJobAd);
    }

    @Test
    public void deleteJobAd_Should_RemoveJobAd_When_PermissionsAreValid() {
        // Arrange
        JobAd mockJobAd = createMockJobAd();
        Company mockCompany = mockJobAd.getCompany();
        JobApplication mockJobApplication = createMockApplication();

        Set<JobApplication> jobApplications = new HashSet<>();
        jobApplications.add(mockJobApplication);

        mockJobAd.setListOfApplicationMatchRequests(jobApplications);
        mockJobApplication.getListOfAdMatchRequests().add(mockJobAd);

        // Act
        mockJobAdService.deleteJobAd(mockJobAd, mockCompany);

        // Assert
        Assertions.assertTrue(mockJobAd.getListOfApplicationMatchRequests().isEmpty());
        Assertions.assertFalse(mockJobApplication.getListOfAdMatchRequests().contains(mockJobAd));
        Mockito.verify(mockJobAdRepository, Mockito.times(1)).delete(mockJobAd);
    }


    @Test
    public void deleteJobAd_Should_ThrowException_When_CompanyDoesNotHavePermission() {
        // Arrange
        JobAd mockJobAd = createMockJobAd();
        Company unauthorizedCompany = createMockCompany();
        unauthorizedCompany.setId(mockJobAd.getCompany().getId() + 1);

        // Act & Assert
        Assertions.assertThrows(AuthorizationException.class,
                () -> mockJobAdService.deleteJobAd(mockJobAd, unauthorizedCompany));

    }

    @Test
    public void deleteJobAd_Should_RemoveJobAdFromCompany_When_JobAdIsDeleted() {
        // Arrange
        JobAd mockJobAd = createMockJobAd();
        Company mockCompany = mockJobAd.getCompany();

        // Act
        mockJobAdService.deleteJobAd(mockJobAd, mockCompany);

        // Assert
        Assertions.assertFalse(mockCompany.getJobAds().contains(mockJobAd));
        Mockito.verify(mockJobAdRepository, Mockito.times(1)).delete(mockJobAd);
    }

    @Test
    public void deleteJobAd_Should_ClearApplicationMatchRequests_When_JobAdIsDeleted() {
        // Arrange
        JobAd mockJobAd = createMockJobAd();
        JobApplication mockJobApplication = createMockApplication();

        Set<JobApplication> jobApplications = new HashSet<>();
        jobApplications.add(mockJobApplication);

        mockJobAd.setListOfApplicationMatchRequests(jobApplications);
        mockJobApplication.getListOfAdMatchRequests().add(mockJobAd);

        // Act
        mockJobAdService.deleteJobAd(mockJobAd, mockJobAd.getCompany());

        // Assert
        Assertions.assertTrue(mockJobAd.getListOfApplicationMatchRequests().isEmpty());
        Assertions.assertFalse(mockJobApplication.getListOfAdMatchRequests().contains(mockJobAd));
    }
}
