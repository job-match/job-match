package com.project.jobmatch.services;

import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.models.JobAd;
import com.project.jobmatch.repositories.interfaces.JobAdRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.project.jobmatch.Helpers.createMockJobAd;

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
}
