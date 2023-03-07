package com.example.apiprojeteasyhealth.controller;

import com.example.apiprojeteasyhealth.dto.AllAboutPatient;
import com.example.apiprojeteasyhealth.dto.PatientAlerte;
import com.example.apiprojeteasyhealth.dto.PatientSuiviDetailsDto;
import com.example.apiprojeteasyhealth.entity.RDV;
import com.example.apiprojeteasyhealth.service.PatientService;
import com.example.apiprojeteasyhealth.service.RDVService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private RDVService rdvService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/{patientMail}")
    @Operation(summary = "Informations patient", description = "Afficher les informations associées à un patient")
    public ResponseEntity<AllAboutPatient> getPatientInfoByEmail(@PathVariable String patientMail) {
        List<AllAboutPatient> result = patientService.getPatientInfoByEmail(patientMail);
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(result.get(0));
        }
    }

    @GetMapping("/alerte/{medecinMail}/{Xjours}")
    @Operation(summary = "Alerte pour patients", description = "Affiche la liste des patients n'ayant pas vu leur médecin (donc un médécin donné) depuis X jours ")
    public List<PatientAlerte> getPatientAlertesByMedecinAndNbJours(@PathVariable String medecinMail, @PathVariable int Xjours){
        return patientService.getPatientAlertesByMedecinAndNbJours(medecinMail, Xjours);
    }

    @GetMapping("medecin/{medecinMail}")
    @Operation(summary = "Affiche les patients suivi par un médecin", description = "Affiche en détails tout les clients suivis par un certain médecin")
    public List<PatientSuiviDetailsDto> getPatientsSuiviDetailsByMedecinAdresseMail(@PathVariable String medecinMail){
        return patientService.getPatientsSuiviDetailsByMedecinAdresseMail(medecinMail);
    }



}