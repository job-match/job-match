package com.project.jobmatch.services.interfaces;

import com.project.jobmatch.models.Company;
import com.project.jobmatch.services.CloudinaryImage;

import java.util.List;

public interface CompanyService {

    void registerCompany(Company company);

    void updateCompany(Company companyAuthenticated, Company companyMapped);

    void deleteCompany(Company companyToDelete, Company companyAuthenticated);

    void uploadPictureToCompany(Company companyAuthenticated, Company companyToUploadPicture, CloudinaryImage cloudinaryImage);

    Company getCompanyByUsername(String username);

    Company getCompanyById(int id);

    List<Company> getAllCompanies();

    List<Company> searchCompanies(String username, String name, String email, String keyword, String location);
}
