package com.project.jobmatch.services.interfaces;


import com.project.jobmatch.services.CloudinaryImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {

    CloudinaryImage upload(MultipartFile multipartFile) throws IOException;
}
