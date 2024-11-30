package com.project.jobmatch.services;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.EntityDuplicateException;
import com.project.jobmatch.models.*;
import com.project.jobmatch.repositories.interfaces.JobAdRepository;
import com.project.jobmatch.repositories.interfaces.JobApplicationRepository;
import com.project.jobmatch.repositories.interfaces.MatchRepository;
import com.project.jobmatch.services.interfaces.JobAdService;
import com.project.jobmatch.services.interfaces.MatchService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.project.jobmatch.Helpers.*;

@ExtendWith(MockitoExtension.class)
public class MatchServiceImplTests {

    @Mock
    private MatchRepository mockMatchRepository;

    @Mock
    private JobAdRepository mockJobAdRepository;

    @Mock
    private JobApplicationRepository mockJobApplicationRepository;

    @InjectMocks
    private MatchServiceImpl mockMatchServiceImpl;

    @Test
    public void confirmMatchWithJobApplication_Should_ThrowException_When_CompanyIsNotOwner() {
        //Arrange
        JobAd mockJobAd = createMockJobAd();
        JobApplication mockJobApplication = createMockApplication();
        Company mockCompany = createMockCompany();
        mockCompany.setId(mockCompany.getId() + 1);

        //Act, Assert
        Assertions.assertThrows(AuthorizationException.class,
                () -> mockMatchServiceImpl.confirmMatchWithJobApplication(mockJobAd, mockJobApplication, mockCompany));
    }


    @Test
    public void confirmMatchWithJobApplication_Should_CreateMatch_When_CompanyIsOwner() {
        //Arrange
        JobAd mockJobAd = createMockJobAd();
        JobApplication mockJobApplication = createMockApplication();
        Company mockCompany = createMockCompany();
        Match mockMatch = createMockMatch();

        //Act
        mockMatchServiceImpl.confirmMatchWithJobApplication(mockJobAd, mockJobApplication, mockCompany);
        mockMatchRepository.save(mockMatch);

        // Assert
        Mockito.verify(mockMatchRepository, Mockito.times(1)).save(mockMatch);
        Mockito.verify(mockJobAdRepository, Mockito.times(1)).save(mockJobAd);

    }

    @Test
    public void confirmMatchWithJobAd_Should_ThrowException_When_ProfessionalIsNotOwner() {
        //Arrange
        JobAd mockJobAd = createMockJobAd();
        JobApplication mockJobApplication = createMockApplication();
        Professional mockProfessional = createMockProfessional();
        mockProfessional.setId(mockProfessional.getId() + 1);

        //Act, Assert
        Assertions.assertThrows(AuthorizationException.class,
                () -> mockMatchServiceImpl.confirmMatchWithJobAd(mockJobAd, mockJobApplication, mockProfessional));
    }

    @Test
    public void confirmMatchWithJobAd_Should_CreateMatch_When_ProfessionalIsOwner() {
        //Arrange
        JobAd mockJobAd = createMockJobAd();
        JobApplication mockJobApplication = createMockApplication();
        Professional mockProfessional = createMockProfessional();
        Match mockMatch = createMockMatch();

        //Act
        mockMatchServiceImpl.confirmMatchWithJobAd(mockJobAd, mockJobApplication, mockProfessional);
        mockMatchRepository.save(mockMatch);

        // Assert
        Mockito.verify(mockMatchRepository, Mockito.times(1)).save(mockMatch);
        Mockito.verify(mockJobApplicationRepository, Mockito.times(1)).save(mockJobApplication);

    }

    @Test
    public void createMatch_Should_ThrowException_When_MatchAlreadyExists() {
        //Arrange
        JobAd mockJobAd = createMockJobAd();
        JobApplication mockJobApplication = createMockApplication();

        //Act
        Mockito.when(mockMatchRepository.existsMatchByJobAdAndJobApplication(mockJobAd, mockJobApplication))
                .thenReturn(true);

        //Assert
        Assertions.assertThrows(EntityDuplicateException.class,
                () -> mockMatchServiceImpl.createMatch(mockJobAd, mockJobApplication));
    }

    @Test
    public void createMatch_Should_CreateMatch_When_NoExistingMatch() {
        // Arrange
        JobAd mockJobAd = createMockJobAd();
        JobApplication mockJobApplication = createMockApplication();
        Match mockMatch = createMockMatch();

        Mockito.when(mockMatchRepository.existsMatchByJobAdAndJobApplication(mockJobAd, mockJobApplication))
                .thenReturn(false);

        // Act
        mockMatchServiceImpl.createMatch(mockJobAd, mockJobApplication);

        // Assert
        Mockito.verify(mockMatchRepository, Mockito.times(1)).save(Mockito.any(Match.class));
    }

    @Test
    public void createMatch_Should_RemoveJobApplicationFromAdRequests_When_ItExists() {
        // Arrange
        JobAd mockJobAd = createMockJobAd();
        JobApplication mockJobApplication = createMockApplication();

        mockJobAd.getListOfApplicationMatchRequests().add(mockJobApplication);

        Mockito.when(mockMatchRepository.existsMatchByJobAdAndJobApplication(mockJobAd, mockJobApplication))
                .thenReturn(false);

        // Act
        mockMatchServiceImpl.createMatch(mockJobAd, mockJobApplication);

        // Assert
        Assertions.assertFalse(mockJobAd.getListOfApplicationMatchRequests().contains(mockJobApplication));
        Mockito.verify(mockJobAdRepository, Mockito.times(1)).save(mockJobAd);
    }

    @Test
    public void createMatch_Should_RemoveJobAdFromApplicationRequests_When_ItExists() {
        // Arrange
        JobAd mockJobAd = createMockJobAd();
        JobApplication mockJobApplication = createMockApplication();

        mockJobApplication.getListOfAdMatchRequests().add(mockJobAd);

        Mockito.when(mockMatchRepository.existsMatchByJobAdAndJobApplication(mockJobAd, mockJobApplication))
                .thenReturn(false);

        // Act
        mockMatchServiceImpl.createMatch(mockJobAd, mockJobApplication);

        // Assert
        Assertions.assertFalse(mockJobApplication.getListOfAdMatchRequests().contains(mockJobAd));
        Mockito.verify(mockJobApplicationRepository, Mockito.times(1)).save(mockJobApplication);
    }

    @Test
    public void createMatch_Should_NotModifyRequests_When_NoneExist() {
        // Arrange
        JobAd mockJobAd = createMockJobAd();
        JobApplication mockJobApplication = createMockApplication();

        Mockito.when(mockMatchRepository.existsMatchByJobAdAndJobApplication(mockJobAd, mockJobApplication))
                .thenReturn(false);

        // Act
        mockMatchServiceImpl.createMatch(mockJobAd, mockJobApplication);

        // Assert
        Mockito.verify(mockJobAdRepository, Mockito.never()).save(mockJobAd);
        Mockito.verify(mockJobApplicationRepository, Mockito.never()).save(mockJobApplication);
    }

    @Test
    public void getMatchedJobApplications_Should_ReturnApplications_When_CompanyHasMatches() {
        // Arrange
        Company mockCompany = createMockCompany();
        JobApplication mockJobApplication1 = createMockApplication();
        JobApplication mockJobApplication2 = createMockApplication();

        Mockito.when(mockMatchRepository.findJobApplicationsByCompany(mockCompany))
                .thenReturn(List.of(mockJobApplication1, mockJobApplication2));

        // Act
        List<JobApplication> result = mockMatchServiceImpl.getMatchedJobApplications(mockCompany);

        // Assert
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.contains(mockJobApplication1));
        Assertions.assertTrue(result.contains(mockJobApplication2));
        Mockito.verify(mockMatchRepository, Mockito.times(1)).findJobApplicationsByCompany(mockCompany);
    }

    @Test
    public void getMatchedJobAds_Should_ReturnAds_When_ProfessionalHasMatches() {
        // Arrange
        Professional mockProfessional = createMockProfessional();
        JobAd mockJobAd1 = createMockJobAd();
        JobAd mockJobAd2 = createMockJobAd();

        Mockito.when(mockMatchRepository.findJobAdByProfessional(mockProfessional))
                .thenReturn(List.of(mockJobAd1, mockJobAd2));

        // Act
        List<JobAd> result = mockMatchServiceImpl.getMatchedJobAds(mockProfessional);

        // Assert
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.contains(mockJobAd1));
        Assertions.assertTrue(result.contains(mockJobAd2));
        Mockito.verify(mockMatchRepository, Mockito.times(1)).findJobAdByProfessional(mockProfessional);
    }

    @Test
    public void getAllMatches_Should_ReturnAllMatches_When_RepositoryIsNotEmpty() {
        // Arrange
        Match mockMatch1 = createMockMatch();
        Match mockMatch2 = createMockMatch();

        Mockito.when(mockMatchRepository.findAll()).thenReturn(List.of(mockMatch1, mockMatch2));

        // Act
        List<Match> result = mockMatchServiceImpl.getAllMatches();

        // Assert
        Assertions.assertEquals(2, result.size());
        Assertions.assertTrue(result.contains(mockMatch1));
        Assertions.assertTrue(result.contains(mockMatch2));
        Mockito.verify(mockMatchRepository, Mockito.times(1)).findAll();
    }

}
