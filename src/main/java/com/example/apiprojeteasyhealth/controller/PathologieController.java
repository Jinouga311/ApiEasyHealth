package com.example.apiprojeteasyhealth.controller;

import com.example.apiprojeteasyhealth.dto.PatientPathologie;
import com.example.apiprojeteasyhealth.entity.Pathologie;
import com.example.apiprojeteasyhealth.service.PathologieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("pathologie")
public class PathologieController {

    @Autowired
    private PathologieService pathologieService;

    @GetMapping("/{patientMail}")
    public List<PatientPathologie> getPathologiesByPatientEmail(@PathVariable String patientMail) {
        return pathologieService.getPathologiesByPatientEmail(patientMail);
    }


}
