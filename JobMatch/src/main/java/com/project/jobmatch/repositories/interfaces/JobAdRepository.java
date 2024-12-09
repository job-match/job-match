package com.project.jobmatch.repositories.interfaces;

import com.project.jobmatch.models.JobAd;
import com.project.jobmatch.models.Location;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;


public interface JobAdRepository extends JpaRepository<JobAd, Integer> {
    Optional<JobAd> findJobAdById(int id);

    Optional<JobAd> findJobAdByPositionTitle(String positionTitle);

    @Override
    List<JobAd> findAll();

    @Query("SELECT j FROM JobAd j " +
            "WHERE (:title IS NULL OR j.positionTitle LIKE %:title%) " +
            "AND (:location IS NULL OR j.location.name LIKE %:location%) " +
            "AND (:minSalary IS NULL OR j.minSalaryBoundary >= :minSalary) " +
            "AND (:maxSalary IS NULL OR j.maxSalaryBoundary <= :maxSalary) " +
            "AND (:requirement IS NULL OR EXISTS (SELECT r FROM j.requirements r WHERE r.type LIKE %:requirement%))")
    List<JobAd> searchJobAds(@Param("title") String title,
                             @Param("location") String location,
                             @Param("minSalary") Double minSalary,
                             @Param("maxSalary") Double maxSalary,
                             @Param("requirement") String requirement);

    List<JobAd> findJobAdsByLocation(Location location);

    @Query("SELECT j FROM JobAd j ORDER BY j.createdAt DESC")
    List<JobAd> getSixMostRecentJobAds();

    @Query("SELECT j FROM JobAd j WHERE j.company.id = :companyId")
    List<JobAd> getJobAdByCompanyId(int companyId);
}
