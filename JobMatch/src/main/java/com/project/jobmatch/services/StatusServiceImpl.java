package com.project.jobmatch.services;

import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.models.Status;
import com.project.jobmatch.repositories.interfaces.StatusRepository;
import com.project.jobmatch.services.interfaces.StatusService;

public class StatusServiceImpl implements StatusService {

    private final StatusRepository statusRepository;

    public StatusServiceImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public Status getStatusByType(String type) {
        return statusRepository
                .findStatusByType(type)
                .orElseThrow(() -> new EntityNotFoundException("Status", "type", type));
    }
}
