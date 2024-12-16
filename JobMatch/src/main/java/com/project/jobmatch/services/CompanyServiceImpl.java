package com.project.jobmatch.services;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.EntityDuplicateException;
import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.models.Company;
import com.project.jobmatch.models.Picture;
import com.project.jobmatch.repositories.interfaces.CompanyRepository;
import com.project.jobmatch.repositories.interfaces.PictureRepository;
import com.project.jobmatch.services.interfaces.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import static com.project.jobmatch.helpers.ServicesConstants.COMPANY;
import static com.project.jobmatch.helpers.ServicesConstants.MODIFY_PROFILE_ERROR_MESSAGE;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final PictureRepository pictureRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository,
                              PictureRepository pictureRepository) {
        this.companyRepository = companyRepository;
        this.pictureRepository = pictureRepository;
    }

    @Override
    public Company getCompanyByUsername(String username) {
        return companyRepository
                .getCompanyByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException(COMPANY, username, "username"));
    }

    @Override
    public Company getCompanyById(int id) {
        return companyRepository
                .getCompanyById(id)
                .orElseThrow(()-> new EntityNotFoundException(COMPANY, id));
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Page<Company> searchCompaniesPaginated(String username, String name, String email, String keyword, String location, PageRequest pageRequest) {
        return companyRepository.searchCompaniesPaginated(username, name, email, keyword, location, pageRequest);
    }

    @Override
    public List<Company> searchCompanies(String username,
                                         String name,
                                         String email,
                                         String keyword,
                                         String location) {
        return companyRepository.searchCompanies(username, name, email, keyword, location);
    }

    @Override
    public Company getCompanyByJobAdId(int jobAdId) {
        return companyRepository
                .findCompanyByJobAdId(jobAdId)
                .orElseThrow(() -> new EntityNotFoundException("Job Ad", jobAdId));
    }

    @Override
    public void registerCompany(Company company) {
        boolean existsDuplicate = true;

        try {
            getCompanyByUsername(company.getUsername());
        } catch (EntityNotFoundException e) {
            existsDuplicate = false;
        }

        if (existsDuplicate) {
            throw new EntityDuplicateException(COMPANY, "username", company.getUsername());
        } else {
            companyRepository.save(company);
        }
    }

    @Override
    public void updateCompany(Company companyAuthenticated, Company companyMapped) {
        checkModifyPermissions(companyAuthenticated, companyMapped);
        companyRepository.save(companyMapped);
    }

    @Override
    public void deleteCompany(Company companyToDelete, Company companyAuthenticated) {
        checkModifyPermissions(companyAuthenticated, companyToDelete);
        companyRepository.delete(companyToDelete);
    }

    @Override
    public void uploadPictureToCompany(Company companyAuthenticated, Company companyToUploadPicture, CloudinaryImage cloudinaryImage) {
        checkModifyPermissions(companyAuthenticated, companyToUploadPicture);

        Picture picture = new Picture();
        picture.setUrl(cloudinaryImage.getUrl());
        picture.setPublicId(cloudinaryImage.getPublicId());

        pictureRepository.save(picture);

        Picture pictureOfCompany = pictureRepository.findPictureByUrl(picture.getUrl());
        companyToUploadPicture.setPicture(pictureOfCompany);

        companyRepository.save(companyToUploadPicture);
    }

    private void checkModifyPermissions(Company companyAuthenticated, Company companyMapped) {
        if (!(companyAuthenticated.equals(companyMapped))) {
            throw new AuthorizationException(MODIFY_PROFILE_ERROR_MESSAGE);
        }
    }


}
