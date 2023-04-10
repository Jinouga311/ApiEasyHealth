package com.example.apiprojeteasyhealth.controller;

import com.example.apiprojeteasyhealth.dto.AllAboutPatient;
import com.example.apiprojeteasyhealth.dto.PatientAlerte;
import com.example.apiprojeteasyhealth.dto.PatientSuiviDetailsDto;
import com.example.apiprojeteasyhealth.entity.Patient;
import com.example.apiprojeteasyhealth.service.PatientService;
import com.example.apiprojeteasyhealth.service.RDVService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

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
    public ResponseEntity<AllAboutPatient> getPatientInfoByEmail(@PathVariable String patientMail) throws IOException {
        List<AllAboutPatient> result = patientService.getPatientInfoByEmail(patientMail);
        System.out.println(result);
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            AllAboutPatient patientInfo = result.get(0);
            if (patientInfo.getPhotoProfil() != null) {
                byte[] fileBytes = Files.readAllBytes(Paths.get(patientInfo.getPhotoProfil()));
                String encodedFile = Base64.getEncoder().encodeToString(fileBytes);
                patientInfo.setPhotoProfil(encodedFile);
            }
            return ResponseEntity.ok(patientInfo);
        }
    }




    @GetMapping("/alerte/{medecinMail}/{Xjours}")
    @Operation(summary = "Alerte pour patients", description = "Affiche la liste des patients n'ayant pas vu leur médecin (donc un médécin donné) depuis X jours ")
    public List<PatientAlerte> getPatientAlertesByMedecinAndNbJours(@PathVariable String medecinMail, @PathVariable int Xjours){
        return patientService.getPatientAlertesByMedecinAndNbJours(medecinMail, Xjours);
    }


    @GetMapping("medecin/{medecinMail}")
    @Operation(summary = "Affiche les patients suivi par un médecin", description = "Affiche en détails tout les clients suivis par un certain médecin")
    public List<PatientSuiviDetailsDto> getPatientsSuiviDetailsByMedecinAdresseMail(@PathVariable String medecinMail) throws IOException, ExecutionException, InterruptedException {
        List<PatientSuiviDetailsDto> patients = patientService.getPatientsSuiviDetailsByMedecinAdresseMail(medecinMail);

        // Utilisation de CompletableFuture pour un traitement parallèle
        List<CompletableFuture<PatientSuiviDetailsDto>> futures = patients.parallelStream().map(patient -> CompletableFuture.supplyAsync(() -> {
            if (patient.getPhotoProfil() != null) {
                try {
                    byte[] fileBytes = Files.readAllBytes(Paths.get(patient.getPhotoProfil()));
                    String encodedFile = Base64.getEncoder().encodeToString(fileBytes);
                    patient.setPhotoProfil(encodedFile);
                } catch (IOException e) {
                    // Gérer l'exception
                    e.printStackTrace();
                }
            }
            return patient;
        })).collect(Collectors.toList());

        // Attente de la fin de toutes les tâches et récupération des résultats
        List<PatientSuiviDetailsDto> convertedPatients = futures.stream().map(CompletableFuture::join).collect(Collectors.toList());

        return convertedPatients;
    }

    @PutMapping(value = "/{patientMail}/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Modifier informations d'un patient", description = "Modifier une ou plusieurs informations d'un patient")
    public ResponseEntity<Patient> updatePatient(
            @PathVariable String patientMail,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "adresseMail", required = false) String adresseMail,
            @RequestParam(value = "numeroTelephone", required = false) String numeroTelephone,
            @RequestParam(value = "pseudo", required = false) String pseudo,
            @RequestParam(value = "motDePasse", required = false) String motDePasse) throws IOException {

        Patient patient = patientService.updatePatient(patientMail, file, adresseMail, numeroTelephone, pseudo, motDePasse);

        return ResponseEntity.ok(patient);
    }




}