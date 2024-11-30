package com.project.jobmatch.services;

import com.project.jobmatch.repositories.interfaces.LocationRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class LocationServiceImplTests {
    @Mock
    private LocationRepository mockLocationRepository;

    @InjectMocks
    LocationServiceImpl mockLocationService;
}
