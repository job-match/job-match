package com.project.jobmatch.services;

import com.project.jobmatch.Helpers;
import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.models.Status;
import com.project.jobmatch.repositories.interfaces.StatusRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StatusServiceImplTests {
    @Mock
    private StatusRepository mockStatusRepository;

    @InjectMocks
    private StatusServiceImpl mockStatusService;

    @Test
    void getStatusByType_Should_CallRepository() {
        // Arrange
        Status status = Helpers.createMockStatus();
        status.setType("Approved");

        // Act
        when(mockStatusRepository.findStatusByType(status.getType()))
                .thenReturn(Optional.of(status));
        Status result = mockStatusService.getStatusByType(status.getType());

        // Assert
        assertNotNull(result);
        assertEquals(status.getType(), result.getType());
        verify(mockStatusRepository, times(1))
                .findStatusByType(status.getType());
    }

    @Test
    void getStatusByType_Should_ThrowException_When_StatusNotFound() {
        // Arrange
        String statusType = "UnknownStatus";

        // Act
        when(mockStatusRepository.findStatusByType(statusType))
                .thenReturn(Optional.empty());

        // Assert
        Exception exception = assertThrows(
                EntityNotFoundException.class,
                () -> mockStatusService.getStatusByType(statusType)
        );

        assertEquals("Status with type UnknownStatus not found.", exception.getMessage());
        verify(mockStatusRepository, times(1)).findStatusByType(statusType);
    }
}
