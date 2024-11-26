package com.project.jobmatch.repositories.interfaces;

import com.project.jobmatch.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {

    Optional<Status> findStatusByType(String type);
}
