package com.project.jobmatch.repositories.interfaces;

import com.project.jobmatch.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {

    Optional<Location> findLocationByName(String name);
}
