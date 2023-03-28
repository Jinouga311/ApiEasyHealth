package com.example.apiprojeteasyhealth.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;


import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class AppConfig {

    @Value("${upload.dir2}")
    private String uploadDir2;

    @Bean
    public Path uploadPath() {
        return Paths.get(uploadDir2);
    }

}
