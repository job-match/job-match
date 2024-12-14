package com.project.jobmatch.services.interfaces;

import com.project.jobmatch.models.Professional;
import com.project.jobmatch.services.CloudinaryImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface ProfessionalService {

    void registerProfessional(Professional professional);

    void updateProfessional(Professional professionalAuthenticated, Professional professionalMapped);

    void uploadPictureToProfessional(Professional professionalAuthenticated, Professional professionalToUploadPicture, CloudinaryImage cloudinaryImage);

    void deleteProfessional(Professional professionalToDelete, Professional professionalAuthenticated);

    Professional getByUsername(String username);

    Professional getProfessionalById(int id);

    Professional getProfessionalByJobApplicationId(int jobApplicationId);

    List<Professional> getAllProfessionals();

    List<Professional> searchProfessionals(String username, String name, String email, String keyword, String location);

    Page<Professional> searchProfessionalsPaginated(String username, String firstName, String email, String keyword, String location, PageRequest pageRequest);
}
