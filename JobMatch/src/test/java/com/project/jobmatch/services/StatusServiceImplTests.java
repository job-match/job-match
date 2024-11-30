package com.project.jobmatch.services;

import com.project.jobmatch.repositories.interfaces.StatusRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StatusServiceImplTests {
    @Mock
    private StatusRepository mockStatusRepository;

    @InjectMocks
    private StatusServiceImpl mockStatusService;
}
