package com.project.jobmatch.repositories.interfaces;

import com.project.jobmatch.models.Professional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfessionalRepository extends JpaRepository<Professional, Integer> {

    Optional<Professional> findProfessionalByUsername(String username);

    Optional<Professional> findProfessionalById(int id);

    @Query("SELECT p FROM Professional p JOIN p.jobApplications ja WHERE ja.id = :jobApplicationId")
    Optional<Professional> findProfessionalByJobApplicationId(@Param("jobApplicationId") int jobApplicationId);

    @Override
    List<Professional> findAll();

    @Query("SELECT p FROM Professional p " +
            "WHERE (:username IS NULL OR p.username LIKE %:username%) " +
            "AND (:name IS NULL OR " +
            "LOWER(p.firstName) LIKE LOWER(CONCAT('%', :name, '%')) OR " +
            "LOWER(p.lastName) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:email IS NULL OR p.email LIKE %:email%) " +
            "AND (:keyword IS NULL OR LOWER(p.summary) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:location IS NULL OR LOWER(p.location.name) LIKE LOWER(CONCAT('%', :location, '%')))")
    List<Professional> searchProfessionals(@Param("username") String username,
                                           @Param("name") String name,
                                           @Param("email") String email,
                                           @Param("keyword") String keyword,
                                           @Param("location") String location);
}
