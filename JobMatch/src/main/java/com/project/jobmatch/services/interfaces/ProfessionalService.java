package com.project.jobmatch.services.interfaces;

import com.project.jobmatch.models.Professional;
import com.project.jobmatch.services.CloudinaryImage;

import java.util.List;

public interface ProfessionalService {
    Professional getByUsername(String username);

    Professional getProfessionalById(int id);

    void registerProfessional(Professional professional);

    List<Professional> getAllProfessionals();

    void updateProfessional(Professional professionalAuthenticated, Professional professionalMapped);

    void uploadPictureToProfessional(Professional professionalAuthenticated, Professional professionalToUploadPicture, CloudinaryImage cloudinaryImage);
}
