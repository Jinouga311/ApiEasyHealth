package com.example.apiprojeteasyhealth.controller;

import com.example.apiprojeteasyhealth.dto.AllAboutMedecin;
import com.example.apiprojeteasyhealth.dto.AllAboutPatient;
import com.example.apiprojeteasyhealth.entity.Medecin;
import com.example.apiprojeteasyhealth.service.MedecinService;
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

@RestController
@RequestMapping("/medecins")
public class MedecinController {

    @Autowired
    private MedecinService medecinService;



    @GetMapping("/{medecinMail}")
    @Operation(summary = "Informations médecin", description = "Afficher les informations associées à un médecin")
    public ResponseEntity<AllAboutMedecin> getPatientInfoByEmail(@PathVariable String medecinMail) throws IOException {
        List<AllAboutMedecin> result = medecinService.getMedecinInfoByEmail(medecinMail);
        System.out.println(result);
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            AllAboutMedecin medecinInfo = result.get(0);
            if (medecinInfo.getPhotoProfil() != null) {
                byte[] fileBytes = Files.readAllBytes(Paths.get(medecinInfo.getPhotoProfil()));
                String encodedFile = Base64.getEncoder().encodeToString(fileBytes);
                medecinInfo.setPhotoProfil(encodedFile);
            }
            return ResponseEntity.ok(medecinInfo);
        }
    }

    @PutMapping(value = "/{medecinMail}/update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Modifier informations d'un médecin", description = "Modifier une ou plusieurs informations d'un médecin")
    public ResponseEntity<Medecin> updateMedecin(
            @PathVariable String medecinMail,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "adresseMail", required = false) String adresseMail,
            @RequestParam(value = "numeroTelephone", required = false) String numeroTelephone,
            @RequestParam(value = "pseudo", required = false) String pseudo,
            @RequestParam(value = "motDePasse", required = false) String motDePasse) {

        Medecin medecin = medecinService.updateMedecin(medecinMail, file, adresseMail, numeroTelephone, pseudo, motDePasse);

        return ResponseEntity.ok(medecin);
    }






}
