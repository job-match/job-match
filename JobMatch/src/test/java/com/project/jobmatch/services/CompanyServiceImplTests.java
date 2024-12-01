package com.project.jobmatch.services;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.EntityDuplicateException;
import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.models.Company;
import com.project.jobmatch.models.Picture;
import com.project.jobmatch.repositories.interfaces.CompanyRepository;
import com.project.jobmatch.repositories.interfaces.PictureRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static com.project.jobmatch.Helpers.*;

public class CompanyServiceImplTests {

    @Mock
    CompanyRepository mockCompanyRepository;

    @Mock
    PictureRepository mockPictureRepository;

    @InjectMocks
    CompanyServiceImpl mockCompanyService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getByUsername_Should_ReturnCompany_When_UsernameExists() {
        Company mockCompany = createMockCompany();

        Mockito.when(mockCompanyRepository.getCompanyByUsername(Mockito.anyString()))
                .thenReturn(Optional.of(mockCompany));

        Company result = mockCompanyService.getCompanyByUsername(Mockito.anyString());

        Assertions.assertEquals(mockCompany, result);
    }

    @Test
    public void getById_Should_ReturnCompany_When_IdExists() {
        Company mockCompany = createMockCompany();

        Mockito.when(mockCompanyRepository.getCompanyById(Mockito.anyInt()))
                .thenReturn(Optional.of(mockCompany));

        Company result = mockCompanyService.getCompanyById(Mockito.anyInt());

        Assertions.assertEquals(mockCompany, result);
    }

    @Test
    public void getAllCompanies_Should_CallRepository() {
        List<Company> mockCompaniesList = new ArrayList<>();

        Mockito.when(mockCompanyRepository.findAll())
                .thenReturn(mockCompaniesList);

        List<Company> result = mockCompanyService.getAllCompanies();

        Assertions.assertEquals(mockCompaniesList, result);
    }

    @Test
    public void searchCompanies_Should_CallRepository() {
        List<Company> mockCompaniesList = new ArrayList<>();

        Mockito.when(mockCompanyRepository.searchCompanies(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString())).thenReturn(mockCompaniesList);

        List<Company> result = mockCompanyService.searchCompanies(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString()
        );

        Assertions.assertEquals(mockCompaniesList, result);
    }

    @Test
    public void registerCompany_Should_CallRepository_When_CompanyWithSameUsernameExists() {
        Company mockCompany = createMockCompany();

        Mockito.when(mockCompanyRepository.getCompanyByUsername(Mockito.anyString()))
                .thenThrow(EntityNotFoundException.class);

        mockCompanyService.registerCompany(mockCompany);

        Mockito.verify(mockCompanyRepository, Mockito.times(1))
                .save(mockCompany);

    }

    @Test
    public void registerCompany_ShouldThrow_When_CompanyWithSameUsernameExists() {
        Company mockCompany = createMockCompany();

        Mockito.when(mockCompanyRepository.getCompanyByUsername(Mockito.anyString()))
                .thenReturn(Optional.of(mockCompany));

        Assertions.assertThrows(EntityDuplicateException.class,
                () -> mockCompanyService.registerCompany(mockCompany));
    }

    @Test
    public void updateCompany_Should_CallRepository_When_CompanyIsOwner() {
        Company mockCompany = createMockCompany();

        mockCompanyService.updateCompany(mockCompany, mockCompany);

        Mockito.verify(mockCompanyRepository, Mockito.times(1))
                .save(mockCompany);
    }

    @Test
    public void updateCompany_Should_ThrowException_When_CompanyIsNotOwner() {
        Company mockCompany = createMockCompany();
        Company mockMappedCompany = createMockCompany();
        mockMappedCompany.setId(2);

        Assertions.assertThrows(
                AuthorizationException.class,
                () -> mockCompanyService.updateCompany(mockCompany, mockMappedCompany));

    }

    @Test
    public void deleteCompany_Should_CallRepository_When_CompanyIsOwner() {
        Company mockCompany = createMockCompany();

        mockCompanyService.deleteCompany(mockCompany, mockCompany);

        Mockito.verify(mockCompanyRepository, Mockito.times(1))
                .delete(mockCompany);
    }

    @Test
    public void deleteCompany_Should_ThrowException_When_CompanyIsNotOwner() {
        Company mockCompany = createMockCompany();
        Company mockMappedCompany = createMockCompany();
        mockMappedCompany.setId(2);

        Assertions.assertThrows(
                AuthorizationException.class,
                () -> mockCompanyService.deleteCompany(mockCompany, mockMappedCompany));

    }

    @Test
    public void uploadPicture_Should_CallRepository_When_CompanyIsOwner() {
        Company mockCompany = createMockCompany();
        CloudinaryImage mockCloudinaryImage = createMockCloudinaryImage();

        mockCompanyService.uploadPictureToCompany(mockCompany, mockCompany, mockCloudinaryImage);

        Mockito.verify(mockPictureRepository, Mockito.times(1))
                .save(Mockito.argThat(picture ->
                        picture.getUrl().equals(mockCloudinaryImage.getUrl()) &&
                                picture.getPublicId().equals(mockCloudinaryImage.getPublicId())
                ));

        Mockito.verify(mockPictureRepository, Mockito.times(1))
                .findPictureByUrl(mockCloudinaryImage.getUrl());

        Mockito.verify(mockCompanyRepository, Mockito.times(1))
                .save(mockCompany);

    }

    @Test
    public void uploadPicture_Should_ThrowException_When_CompanyIsNotOwner() {
        Company mockCompany = createMockCompany();
        Company mockMappedCompany = createMockCompany();
        mockMappedCompany.setId(2);
        CloudinaryImage mockCloudinaryImage = createMockCloudinaryImage();

        Assertions.assertThrows(
                AuthorizationException.class,
                () -> mockCompanyService.uploadPictureToCompany(
                        mockCompany,
                        mockMappedCompany,
                        mockCloudinaryImage));
    }


}
