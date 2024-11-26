package com.project.jobmatch.services;

import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.models.Location;
import com.project.jobmatch.repositories.interfaces.LocationRepository;
import com.project.jobmatch.services.interfaces.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Location getLocationByName(String name) {
        return locationRepository
                .findLocationByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Location", "name", name));
    }
}
