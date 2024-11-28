package com.project.jobmatch.repositories.interfaces;

import com.project.jobmatch.models.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Integer> {

    List<JobApplication> findJobApplicationsByProfessionalId(int id);

    @Override
    List<JobApplication> findAll();

    Optional<JobApplication> findJobApplicationById(int jobApplicationId);

    @Query("SELECT j FROM JobApplication j " +
            "WHERE (:location IS NULL OR j.location.name LIKE %:location%) " +
            "AND (:minSalary IS NULL OR j.minDesiredSalary >= :minSalary) " +
            "AND (:maxSalary IS NULL OR j.maxDesiredSalary <= :maxSalary) " +
            "AND (:skill IS NULL OR EXISTS (SELECT s FROM j.skills s WHERE s.type LIKE %:skill%))" +
            "AND (:keyword IS NULL OR LOWER(j.motivationLetter) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<JobApplication> searchJobApplications(@Param("location") String location,
                                               @Param("minSalary") Double minSalary,
                                               @Param("maxSalary") Double maxSalary,
                                               @Param("skill") String skill,
                                               @Param("keyword") String keyword);
}
