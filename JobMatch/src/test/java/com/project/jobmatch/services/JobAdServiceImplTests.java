package com.project.jobmatch.services;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.exceptions.MatchRequestDeniedException;
import com.project.jobmatch.models.*;
import com.project.jobmatch.repositories.interfaces.JobAdRepository;
import com.project.jobmatch.services.interfaces.MatchService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.*;

import static com.project.jobmatch.Helpers.*;
import static com.project.jobmatch.helpers.ServicesConstants.*;

@ExtendWith(MockitoExtension.class)
public class JobAdServiceImplTests {

    @Mock
    JobAdRepository mockJobAdRepository;

    @Mock
    MatchService mockMatchService;

    @Mock
    MailjetServiceImpl mockMailjetService;

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
    public void searchJobAdsPaginated_Should_CallRepositoryWithCorrectParameters() {
        // Arrange
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<JobAd> mockPage = Mockito.mock(Page.class);

        Mockito.when(mockJobAdRepository.searchJobAdsPaginated(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyDouble(),
                Mockito.anyDouble(),
                Mockito.anyString(),
                Mockito.any(PageRequest.class))
        ).thenReturn(mockPage);

        // Act
        Page<JobAd> result = mockJobAdService.searchJobAdsPaginated(
                "Developer",
                "City",
                1000.0,
                2000.0,
                "Java",
                pageRequest
        );

        // Assert
        Assertions.assertEquals(mockPage, result);
        Mockito.verify(mockJobAdRepository, Mockito.times(1))
                .searchJobAdsPaginated("Developer", "City", 1000.0, 2000.0, "Java", pageRequest);
    }

    @Test
    public void getJobAdsByLocation_Should_ReturnListOfJobAds_When_LocationExists() {
        // Arrange
        Location mockLocation = createMockLocation();
        List<JobAd> mockJobAds = List.of(createMockJobAd(), createMockJobAd());

        Mockito.when(mockJobAdRepository.findJobAdsByLocation(mockLocation))
                .thenReturn(mockJobAds);

        // Act
        List<JobAd> result = mockJobAdService.getJobAdsByLocation(mockLocation);

        // Assert
        Assertions.assertEquals(mockJobAds, result);
        Mockito.verify(mockJobAdRepository, Mockito.times(1))
                .findJobAdsByLocation(mockLocation);
    }

    @Test
    public void getJobAdsByLocation_Should_ReturnEmptyList_When_NoJobAdsFound() {
        // Arrange
        Location mockLocation = createMockLocation();

        Mockito.when(mockJobAdRepository.findJobAdsByLocation(mockLocation))
                .thenReturn(Collections.emptyList());

        // Act
        List<JobAd> result = mockJobAdService.getJobAdsByLocation(mockLocation);

        // Assert
        Assertions.assertTrue(result.isEmpty());
        Mockito.verify(mockJobAdRepository, Mockito.times(1))
                .findJobAdsByLocation(mockLocation);
    }

    @Test
    public void getSixMostRecentJobAds_Should_ReturnSixJobAds_When_RepositoryReturnsMoreThanSix() {
        // Arrange
        List<JobAd> mockJobAds = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mockJobAds.add(createMockJobAd());
        }

        Mockito.when(mockJobAdRepository.getSixMostRecentJobAds())
                .thenReturn(mockJobAds);

        // Act
        List<JobAd> result = mockJobAdService.getSixMostRecentJobAds();

        // Assert
        Assertions.assertEquals(6, result.size());
        Mockito.verify(mockJobAdRepository, Mockito.times(1))
                .getSixMostRecentJobAds();
    }

    @Test
    public void getSixMostRecentJobAds_Should_ReturnAllJobAds_When_RepositoryReturnsLessThanSix() {
        // Arrange
        List<JobAd> mockJobAds = List.of(createMockJobAd(), createMockJobAd());

        Mockito.when(mockJobAdRepository.getSixMostRecentJobAds())
                .thenReturn(mockJobAds);

        // Act
        List<JobAd> result = mockJobAdService.getSixMostRecentJobAds();

        // Assert
        Assertions.assertEquals(mockJobAds, result);
        Mockito.verify(mockJobAdRepository, Mockito.times(1))
                .getSixMostRecentJobAds();
    }

    @Test
    public void getJobAdsByCompanyId_Should_ReturnJobAds_When_CompanyIdExists() {
        // Arrange
        int companyId = 1;
        List<JobAd> mockJobAds = List.of(createMockJobAd(), createMockJobAd());

        Mockito.when(mockJobAdRepository.getJobAdByCompanyId(companyId))
                .thenReturn(mockJobAds);

        // Act
        List<JobAd> result = mockJobAdService.getJobAdsByCompanyId(companyId);

        // Assert
        Assertions.assertEquals(mockJobAds, result);
        Mockito.verify(mockJobAdRepository, Mockito.times(1))
                .getJobAdByCompanyId(companyId);
    }

    @Test
    public void getJobAdsByCompanyId_Should_ReturnEmptyList_When_NoJobAdsFound() {
        // Arrange
        int companyId = 1;

        Mockito.when(mockJobAdRepository.getJobAdByCompanyId(companyId))
                .thenReturn(Collections.emptyList());

        // Act
        List<JobAd> result = mockJobAdService.getJobAdsByCompanyId(companyId);

        // Assert
        Assertions.assertTrue(result.isEmpty());
        Mockito.verify(mockJobAdRepository, Mockito.times(1))
                .getJobAdByCompanyId(companyId);
    }

    @Test
    public void getAllActiveJobAdsOfCompany_Should_ReturnActiveJobAds_When_CompanyHasAds() {
        // Arrange
        Company mockCompany = createMockCompany();
        List<JobAd> mockJobAds = List.of(createMockJobAd(), createMockJobAd());

        Mockito.when(mockJobAdRepository.findActiveJobAdsOfCompany(mockCompany.getId(), 1))
                .thenReturn(mockJobAds);

        // Act
        List<JobAd> result = mockJobAdService.getAllActiveJobAdsOfCompany(mockCompany);

        // Assert
        Assertions.assertEquals(mockJobAds, result);
        Mockito.verify(mockJobAdRepository, Mockito.times(1))
                .findActiveJobAdsOfCompany(mockCompany.getId(), 1);
    }

    @Test
    public void getAllActiveJobAdsOfCompany_Should_ReturnEmptyList_When_NoActiveJobAdsFound() {
        // Arrange
        Company mockCompany = createMockCompany();

        Mockito.when(mockJobAdRepository.findActiveJobAdsOfCompany(mockCompany.getId(), 1))
                .thenReturn(Collections.emptyList());

        // Act
        List<JobAd> result = mockJobAdService.getAllActiveJobAdsOfCompany(mockCompany);

        // Assert
        Assertions.assertTrue(result.isEmpty());
        Mockito.verify(mockJobAdRepository, Mockito.times(1))
                .findActiveJobAdsOfCompany(mockCompany.getId(), 1);
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
    public void updateJobAd_Should_ThrowException_When_CompanyDoesNotHavePermission() {
        // Arrange
        JobAd mockJobAd = createMockJobAd();
        Company unauthorizedCompany = createMockCompany();
        unauthorizedCompany.setId(mockJobAd.getCompany().getId() + 1);

        // Act & Assert
        Assertions.assertThrows(AuthorizationException.class,
                () -> mockJobAdService.deleteJobAd(mockJobAd, unauthorizedCompany));

    }

    @Test
    public void updateJobAd_Should_UpdateJobAd_When_CompanyHasPermission() {
        // Arrange
        JobAd mockJobAd = createMockJobAd();
        Company authorizedCompany = createMockCompany();

        //Act
        mockJobAdService.updateJobAd(mockJobAd, authorizedCompany);

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

    @Test
    public void addJobApplicationToListOfApplicationMatchRequests_Should_CreateMatch_When_AlreadyExistsInListOfAdMatchRequests() {
        // Arrange
        JobAd mockJobAd = createMockJobAd();
        JobApplication mockJobApplication = createMockApplication();

        mockJobApplication.getListOfAdMatchRequests().add(mockJobAd);

        // Act
        mockJobAdService.addJobApplicationToListOfApplicationMatchRequests(mockJobAd, mockJobApplication);

        // Assert
        Mockito.verify(mockMatchService, Mockito.times(1))
                .createMatch(mockJobAd, mockJobApplication);
    }

    @Test
    public void addJobApplicationToListOfApplicationMatchRequests_Should_SendEmailToBothParties_WhenJobAppContainsJobAdd() {
        JobAd mockJobAd = createMockJobAd();
        JobApplication mockJobApplication = createMockApplication();

        mockJobApplication.getListOfAdMatchRequests().add(mockJobAd);

        mockJobAdService.addJobApplicationToListOfApplicationMatchRequests(mockJobAd, mockJobApplication);

        Mockito.verify(mockMatchService).createMatch(mockJobAd, mockJobApplication);
        Mockito.verify(mockMailjetService, Mockito.times(1)).sendEmail(
                Mockito.eq(mockJobAd.getCompany().getEmail()),
                Mockito.eq(mockJobAd.getCompany().getName()),
                Mockito.eq(SUCCESSFUL_MATCH_SUBJECT_MESSAGE),
                Mockito.eq(SUCCESSFUL_MATCH_TEXT_CONTENT),
                Mockito.anyString()
        );
        Mockito.verify(mockMailjetService, Mockito.times(1)).sendEmail(
                Mockito.eq(mockJobApplication.getProfessional().getEmail()),
                Mockito.eq(mockJobApplication.getProfessional().getFirstName()),
                Mockito.eq(SUCCESSFUL_MATCH_SUBJECT_MESSAGE),
                Mockito.eq(SUCCESSFUL_MATCH_TEXT_CONTENT),
                Mockito.anyString()
        );

    }


    @Test
    public void addJobApplicationToListOfApplicationMatchRequests_Should_Throw_When_RequestDenied() {
        // Arrange
        JobApplication mockJobApplication = createMockApplication();
        JobAd mockJobAd = createMockJobAd();

        mockJobAd.getListOfApplicationMatchRequests().add(mockJobApplication);

        // Act & Assert
        Assertions.assertThrows(MatchRequestDeniedException.class, () ->
                mockJobAdService.addJobApplicationToListOfApplicationMatchRequests(mockJobAd, mockJobApplication)
        );

        Mockito.verify(mockJobAdRepository, Mockito.never()).save(mockJobAd);
    }

    @Test
    void checkSalaryMatch_Should_ReturnTrue_When_SalariesMatch() {
        // Act
        boolean result = mockJobAdService.checkSalaryMatch(2000.0, 3000.0, 1800.0, 3200.0);

        // Assert
        Assertions.assertTrue(result);
    }

    @Test
    void checkSalaryMatch_Should_ReturnFalse_When_SalariesDoNotMatch() {
        // Act
        boolean result = mockJobAdService.checkSalaryMatch(2000.0, 3000.0, 1500.0, 1900.0);

        // Assert
        Assertions.assertFalse(result);
    }

    @Test
    void checkSkillsAndRequirements_Should_ReturnFalse_When_RequirementsNotMet() {
        // Arrange
        Set<Skill> mockSkills = new HashSet<>();

        Requirement mockRequirement = createMockRequirement();
        mockRequirement.setType("NotMetRequirement");
        Set<Requirement> mockRequirements = new HashSet<>();
        mockRequirements.add(mockRequirement);

        // Act
        boolean result = mockJobAdService.checkSkillsAndRequirements(mockSkills, mockRequirements);

        // Assert
        Assertions.assertFalse(result);
    }

    @Test
    void checkLocations_Should_ReturnTrue_When_JobAdLocationIsRemote() {
        // Act
        boolean result = mockJobAdService.checkLocationMatch("REMOTE", "SomeLocation");

        // Assert
        Assertions.assertTrue(result);
    }


    @Test
    void checkLocations_Should_ReturnTrue_When_JobAdLocationIsHybrid() {
        // Act
        boolean result = mockJobAdService.checkLocationMatch("Hybrid", "CityA");

        // Assert
        Assertions.assertTrue(result);
    }


    @Test
    void checkLocations_Should_ReturnFalse_When_LocationIsDifferent() {
        // Act
        boolean result = mockJobAdService.checkLocationMatch("CityA", "CityB");

        // Assert
        Assertions.assertFalse(result);
    }

    @Test
    void checkLocations_Should_ReturnTrue_When_LocationAreTheSame() {
        // Act
        boolean result = mockJobAdService.checkLocationMatch("CityA", "CityA");

        // Assert
        Assertions.assertTrue(result);
    }
}
