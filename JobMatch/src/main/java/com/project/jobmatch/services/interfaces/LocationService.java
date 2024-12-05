package com.project.jobmatch.services.interfaces;

import com.project.jobmatch.models.Location;

import java.util.List;

public interface LocationService {

    Location getLocationByName(String name);

    List<Location> getAllLocations();
}
