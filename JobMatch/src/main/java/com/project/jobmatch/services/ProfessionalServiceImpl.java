package com.project.jobmatch.services;

import com.project.jobmatch.exceptions.AuthorizationException;
import com.project.jobmatch.exceptions.EntityDuplicateException;
import com.project.jobmatch.exceptions.EntityNotFoundException;
import com.project.jobmatch.models.Picture;
import com.project.jobmatch.models.Professional;
import com.project.jobmatch.repositories.interfaces.PictureRepository;
import com.project.jobmatch.repositories.interfaces.ProfessionalRepository;
import com.project.jobmatch.services.interfaces.ProfessionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessionalServiceImpl implements ProfessionalService {
    private static final String MODIFY_PROFESSIONAL_ERROR_MESSAGE =
            "Only owner can make changes to the professional info.";

    private final ProfessionalRepository professionalRepository;
    private final PictureRepository pictureRepository;

    @Autowired
    public ProfessionalServiceImpl(ProfessionalRepository professionalRepository,
                                   PictureRepository pictureRepository) {
        this.professionalRepository = professionalRepository;
        this.pictureRepository = pictureRepository;
    }

    @Override
    public Professional getByUsername(String username) {
        return professionalRepository
                .findProfessionalByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("Professional", username, "username"));
    }

    @Override
    public Professional getProfessionalById(int id) {
        return professionalRepository
                .findProfessionalById(id)
                .orElseThrow(() -> new EntityNotFoundException("Professional", id));
    }

    @Override
    public void registerProfessional(Professional professional) {

        boolean existsDuplicate = true;

        try {
            getByUsername(professional.getUsername());
        } catch (EntityNotFoundException e) {
            existsDuplicate = false;
        }

        if (existsDuplicate) {
            throw new EntityDuplicateException("Professional", "username", professional.getUsername());
        } else {
            professionalRepository.save(professional);
        }
    }

    @Override
    public List<Professional> getAllProfessionals() {
        return professionalRepository.findAll();
    }

    @Override
    public void updateProfessional(Professional professionalAuthenticated,
                                   Professional professionalMapped) {

        checkModifyPermissions(professionalAuthenticated, professionalMapped);
        professionalRepository.save(professionalMapped);
    }

    @Override
    public void uploadPictureToProfessional(Professional professionalAuthenticated,
                                            Professional professionalToUploadPicture,
                                            CloudinaryImage cloudinaryImage) {

        checkModifyPermissions(professionalAuthenticated, professionalToUploadPicture);

        Picture picture = new Picture();
        picture.setUrl(cloudinaryImage.getUrl());
        picture.setPublicId(cloudinaryImage.getPublicId());

        pictureRepository.save(picture);

        Picture pictureOfProfessional = pictureRepository.findPictureByUrl(picture.getUrl());
        professionalToUploadPicture.setPicture(pictureOfProfessional);

        professionalRepository.save(professionalToUploadPicture);
    }

    private void checkModifyPermissions(Professional professionalAuthenticated,
                                        Professional professionalMapped) {

        if (!(professionalAuthenticated.equals(professionalMapped))) {
            throw new AuthorizationException(MODIFY_PROFESSIONAL_ERROR_MESSAGE);
        }
    }
}
