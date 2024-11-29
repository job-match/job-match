package com.project.jobmatch.services;

import com.project.jobmatch.exceptions.EntityDuplicateException;
import com.project.jobmatch.exceptions.EntityNotFoundException;
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



































}
