package com.example.apiprojeteasyhealth.controller;

import com.example.apiprojeteasyhealth.entity.Fichier;
import com.example.apiprojeteasyhealth.service.FichierService;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/")
public class FichierController {

    @Autowired
    FichierService fichierService;

    @Operation(summary = "Upload a file")
    @PostMapping(value = "upload/{mailPatient}/{mailMedecin}", consumes = { "multipart/form-data" })
    @ApiResponse(responseCode = "200", description = "File uploaded successfully")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    public ResponseEntity<?> uploadFile(
            @RequestParam("file") MultipartFile file,
            @PathVariable String mailPatient,
            @PathVariable String mailMedecin
    ) {
        try {
            fichierService.uploadFile(file, mailPatient, mailMedecin);
            return ResponseEntity.ok("File uploaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/files/{mailPatient}/{mailMedecin}")
    public ResponseEntity<List<byte[]>> getFilesByPatientAndMedecin(@PathVariable String mailPatient, @PathVariable String mailMedecin) throws IOException {
        List<Fichier> fichiers = fichierService.getFilesByPatientAndMedecin(mailPatient, mailMedecin);
        List<byte[]> bytesList = new ArrayList<>();

        for (Fichier fichier : fichiers) {
            byte[] bytes = Files.readAllBytes(Paths.get(fichier.getCheminFichier()));
            bytesList.add(bytes);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(bytesList, headers, HttpStatus.OK);
    }




}
