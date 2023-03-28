package com.example.apiprojeteasyhealth;

import com.example.apiprojeteasyhealth.configuration.SwaggerConfig;
import com.example.apiprojeteasyhealth.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ApiProjetEasyHealthApplication extends SpringBootServletInitializer {




    public static void main(String[] args) {
        SpringApplication.run(ApiProjetEasyHealthApplication.class, args);
    }

}
