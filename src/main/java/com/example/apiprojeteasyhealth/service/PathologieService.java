package com.example.apiprojeteasyhealth.service;

import com.example.apiprojeteasyhealth.dto.PatientPathologie;
import com.example.apiprojeteasyhealth.entity.Pathologie;
import com.example.apiprojeteasyhealth.repository.PathologieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PathologieService {

    @Autowired
    private PathologieRepository pathologieRepository;

    public List<PatientPathologie> getPathologiesByPatientEmail(String email) {
        PatientPathologie.resetId();
        return pathologieRepository.findDistinctByPatientAdresseMail(email);
    }


}
