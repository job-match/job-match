package com.project.jobmatch.services;

import com.project.jobmatch.Helpers;
import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.models.Location;
import com.project.jobmatch.repositories.interfaces.LocationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LocationServiceImplTests {
    @Mock
    private LocationRepository mockLocationRepository;

    @InjectMocks
    LocationServiceImpl mockLocationService;

    @Test
    void getLocationByName_Should_CallRepository() {
        // Arrange
        Location location = Helpers.createMockLocation();

        // Act
        when(mockLocationRepository.findLocationByName(location.getName()))
                .thenReturn(Optional.of(location));
        Location result = mockLocationService.getLocationByName(location.getName());

        // Assert
        assertNotNull(result);
        assertEquals(location.getName(), result.getName());
        verify(mockLocationRepository, times(1))
                .findLocationByName(location.getName());
    }

    @Test
    void getLocationByName_Should_ThrowException_When_LocationNotFound() {
        // Arrange
        String locationName = "UnknownCity";

        // Act
        when(mockLocationRepository.findLocationByName(locationName))
                .thenReturn(Optional.empty());

        // Assert
        Exception exception = assertThrows(
                EntityNotFoundException.class,
                () -> mockLocationService.getLocationByName(locationName)
        );

        assertEquals("Location with name UnknownCity not found.", exception.getMessage());
        verify(mockLocationRepository, times(1)).findLocationByName(locationName);
    }
}
