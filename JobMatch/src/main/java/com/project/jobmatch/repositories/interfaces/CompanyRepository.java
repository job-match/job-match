package com.project.jobmatch.repositories.interfaces;

import com.project.jobmatch.models.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

    Optional<Company> getCompanyByUsername(String username);

    Optional<Company> getCompanyById(int id);

    @Override
    List<Company> findAll();

    @Query("SELECT c FROM Company c " +
            "WHERE (:username IS NULL OR c.username LIKE %:username%) " +
            "AND (:name IS NULL OR c.name LIKE %:name%) " +
            "AND (:email IS NULL OR c.email LIKE %:email%) " +
            "AND (:keyword IS NULL OR " +
            "LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.contacts) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:location IS NULL OR LOWER(c.location.name) LIKE LOWER(CONCAT('%', :location, '%')))")
    List<Company> searchCompanies(@Param("username") String username,
                                  @Param("name") String name,
                                  @Param("email") String email,
                                  @Param("keyword") String keyword,
                                  @Param("location") String location);


    @Query("SELECT c FROM Company c " +
            "WHERE (:username IS NULL OR c.username LIKE %:username%) " +
            "AND (:name IS NULL OR c.name LIKE %:name%) " +
            "AND (:email IS NULL OR c.email LIKE %:email%) " +
            "AND (:keyword IS NULL OR " +
            "LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(c.contacts) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:location IS NULL OR LOWER(c.location.name) LIKE LOWER(CONCAT('%', :location, '%')))")
    Page<Company> searchCompaniesPaginated(@Param("username") String username,
                                           @Param("name") String name,
                                           @Param("email") String email,
                                           @Param("keyword") String keyword,
                                           @Param("location") String location,
                                           Pageable pageable);

}
