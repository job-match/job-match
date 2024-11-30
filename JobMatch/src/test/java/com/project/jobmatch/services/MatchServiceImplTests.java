package com.project.jobmatch.services;

import com.project.jobmatch.exceptions.AuthorizationException;
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

//    @Test
//    public void confirmMatchWithJobAd_Should_CreateMatch_When_ProfessionalIsOwner() {
//        //Arrange
//        JobAd mockJobAd = createMockJobAd();
//        JobApplication mockJobApplication = createMockApplication();
//        Professional mockProfessional = createMockProfessional();
//        Match mockMatch = createMockMatch();
//
//        //Act
//        mockMatchServiceImpl.confirmMatchWithJobAd(mockJobAd, mockJobApplication, mockProfessional);
//        mockMatchRepository.save(mockMatch);
//
//        // Assert
//        Mockito.verify(mockMatchRepository, Mockito.times(1)).save(mockMatch);
//        Mockito.verify(mockJobApplicationRepository, Mockito.times(1)).save(mockJobApplication);
//
//    }
//
//
//    @Test
//    public void confirmMatchWithJobAd_Should_ThrowException_When_ProfessionalIsNotOwner() {
//        //Arrange
//        JobAd mockJobAd = createMockJobAd();
//        JobApplication mockJobApplication = createMockApplication();
//        Professional mockProfessional = createMockProfessional();
//        mockProfessional.setId(mockProfessional.getId() + 1);
//
//        //Act, Assert
//        Assertions.assertThrows(AuthorizationException.class,
//                () -> mockMatchServiceImpl.confirmMatchWithJobAd(mockJobAd, mockJobApplication, mockProfessional));
//    }
}
