package com.project.jobmatch.services.interfaces;

import com.project.jobmatch.models.Company;
import com.project.jobmatch.services.CloudinaryImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface CompanyService {

    void registerCompany(Company company);

    void updateCompany(Company companyAuthenticated, Company companyMapped);

    void deleteCompany(Company companyToDelete, Company companyAuthenticated);

    void uploadPictureToCompany(Company companyAuthenticated, Company companyToUploadPicture, CloudinaryImage cloudinaryImage);

    Company getCompanyByUsername(String username);

    Company getCompanyById(int id);

    List<Company> getAllCompanies();

    Page<Company> searchCompaniesPaginated(String username, String name, String email, String keyword, String location, PageRequest pageRequest);

    List<Company> searchCompanies(String username, String name, String email, String keyword, String location);

    Company getCompanyByJobAdId(int jobAdId);
}
