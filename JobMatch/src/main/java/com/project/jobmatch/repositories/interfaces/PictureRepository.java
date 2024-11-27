package com.project.jobmatch.repositories.interfaces;

import com.project.jobmatch.models.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends JpaRepository<Picture, Integer> {

    Picture findPictureByUrl(String url);
}
