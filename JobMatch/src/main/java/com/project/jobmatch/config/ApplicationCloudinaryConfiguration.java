package com.project.jobmatch.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class ApplicationCloudinaryConfiguration {

    private final CloudinaryConfiguration cloudinaryConfiguration;

    public ApplicationCloudinaryConfiguration(CloudinaryConfiguration cloudinaryConfiguration) {
        this.cloudinaryConfiguration = cloudinaryConfiguration;
    }

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(
                Map.of(
                        "cloud_name", cloudinaryConfiguration.getCloudName(),
                        "api_key", cloudinaryConfiguration.getApiKey(),
                        "api_secret", cloudinaryConfiguration.getApiSecret()
                )
        );
    }
}
