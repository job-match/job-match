package com.project.jobmatch.services;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.EntityDuplicateException;
import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.models.Company;
import com.project.jobmatch.models.Picture;
import com.project.jobmatch.models.Professional;
import com.project.jobmatch.repositories.interfaces.PictureRepository;
import com.project.jobmatch.repositories.interfaces.ProfessionalRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.project.jobmatch.Helpers.*;

public class ProfessionalServiceImplTests {

    @Mock
    ProfessionalRepository mockProfessionalRepository;

    @Mock
    PictureRepository mockPictureRepository;

    @InjectMocks
    ProfessionalServiceImpl mockProfessionalService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getByUsername_Should_ReturnProfessional_When_UsernameExists() {
        Professional mockProfessional = createMockProfessional();

        Mockito.when(mockProfessionalRepository.findProfessionalByUsername(Mockito.anyString()))
                .thenReturn(Optional.of(mockProfessional));

        Professional result = mockProfessionalService.getByUsername(Mockito.anyString());

        Assertions.assertEquals(mockProfessional, result);
    }

    @Test
    public void getById_Should_ReturnProfessional_When_IdExists() {
        Professional mockProfessional = createMockProfessional();

        Mockito.when(mockProfessionalRepository.findProfessionalById(Mockito.anyInt()))
                .thenReturn(Optional.of(mockProfessional));

        Professional result = mockProfessionalService.getProfessionalById(Mockito.anyInt());

        Assertions.assertEquals(mockProfessional, result);
    }

    @Test
    public void registerProfessional_Should_CallRepository_When_ProfessionalWithSameUsernameExists() {
        Professional mockProfessional = createMockProfessional();

        Mockito.when(mockProfessionalRepository.findProfessionalByUsername(Mockito.anyString()))
                .thenThrow(EntityNotFoundException.class);

        mockProfessionalService.registerProfessional(mockProfessional);

        Mockito.verify(mockProfessionalRepository, Mockito.times(1))
                .save(mockProfessional);
    }

    @Test
    public void registerProfessional_ShouldThrow_When_ProfessionalWithSameUsernameNameExists() {
        Professional mockProfessional = createMockProfessional();

        Mockito.when(mockProfessionalRepository.findProfessionalByUsername(Mockito.anyString()))
                .thenReturn(Optional.of(mockProfessional));

        Assertions.assertThrows(EntityDuplicateException.class,
                () -> mockProfessionalService.registerProfessional(mockProfessional));
    }

    @Test
    public void getAllProfessionals_Should_CallRepository() {
        List<Professional> mockProfessionalsList = new ArrayList<>();

        Mockito.when(mockProfessionalRepository.findAll())
                .thenReturn(mockProfessionalsList);

        List<Professional> result = mockProfessionalService.getAllProfessionals();

        Assertions.assertEquals(mockProfessionalsList, result);
    }

    @Test
    public void searchProfessionals_Should_CallRepository() {
        List<Professional> mockProfessionalsList = new ArrayList<>();

        Mockito.when(mockProfessionalRepository.searchProfessionals(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString())).thenReturn(mockProfessionalsList);

        List<Professional> result = mockProfessionalService.searchProfessionals(
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString(),
                Mockito.anyString()
        );

        Assertions.assertEquals(mockProfessionalsList, result);
    }

    @Test
    public void getProfessionalByJobApplicationId_Should_ReturnProfessional() {
        Professional mockProfessional = createMockProfessional();

        Mockito.when(mockProfessionalRepository.findProfessionalByJobApplicationId(Mockito.anyInt()))
                .thenReturn(Optional.of(mockProfessional));

        Professional result = mockProfessionalService.getProfessionalByJobApplicationId(Mockito.anyInt());

        Assertions.assertEquals(result, mockProfessional);
    }

    @Test
    public void updateProfessional_Should_CallRepository_When_ProfessionalIsOwner() {
        Professional mockProfessional = createMockProfessional();

        mockProfessionalService.updateProfessional(mockProfessional, mockProfessional);

        Mockito.verify(mockProfessionalRepository, Mockito.times(1))
                .save(mockProfessional);
    }

    @Test
    public void updateProfessional_Should_ThrowException_When_ProfessionalIsNotOwner() {
        Professional mockProfessional = createMockProfessional();
        Professional mockMappedProfessional = createMockProfessional();
        mockMappedProfessional.setId(2);

        Assertions.assertThrows(
                AuthorizationException.class,
                () -> mockProfessionalService.updateProfessional(mockMappedProfessional, mockProfessional));

    }

    @Test
    public void deleteProfessional_Should_CallRepository_When_ProfessionalIsOwner() {
        Professional mockProfessional = createMockProfessional();

        mockProfessionalService.deleteProfessional(mockProfessional, mockProfessional);

        Mockito.verify(mockProfessionalRepository, Mockito.times(1))
                .delete(mockProfessional);
    }

    @Test
    public void deleteProfessional_Should_ThrowException_When_ProfessionalIsNotOwner() {
        Professional mockProfessional = createMockProfessional();
        Professional mockMappedProfessional = createMockProfessional();
        mockMappedProfessional.setId(2);

        Assertions.assertThrows(
                AuthorizationException.class,
                () -> mockProfessionalService.deleteProfessional(mockMappedProfessional, mockProfessional));

    }

    @Test
    public void uploadPictureToProfessional_Should_CallRepository_WhenProfessionalIsOwner() {
        Professional mockProfessional = createMockProfessional();
        CloudinaryImage mockCloudinaryImage = createMockCloudinaryImage();
        Picture mockPicture = createMockPicture();

        mockProfessionalService.uploadPictureToProfessional(mockProfessional, mockProfessional, mockCloudinaryImage);

        Mockito.verify(mockPictureRepository, Mockito.times(1))
                .save(mockPicture);
        Mockito.verify(mockProfessionalRepository, Mockito.times(1))
                .save(mockProfessional);
    }



































}
