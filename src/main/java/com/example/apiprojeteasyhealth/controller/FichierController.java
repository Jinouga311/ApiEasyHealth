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
import java.nio.file.Path;
import java.nio.file.Paths;
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

    @GetMapping(value = "/files/{mailPatient}/{mailMedecin}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Operation(summary = "Get files by patient and medecin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of files",
                    content = @Content(mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = Fichier.class))))
    })
    public ResponseEntity<InputStreamResource> getFilesByPatientAndMedecin(@PathVariable String mailPatient, @PathVariable String mailMedecin) {
        List<Fichier> fichiers = fichierService.getFilesByPatientAndMedecin(mailPatient, mailMedecin);

        // Générer un fichier zip contenant les fichiers retournés
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (ZipOutputStream zos = new ZipOutputStream(baos)) {
            for (Fichier fichier : fichiers) {
                Path filePath = Paths.get(fichier.getCheminFichier());
                File file = filePath.toFile();
                ZipEntry zipEntry = new ZipEntry(fichier.getNomFichier());
                zos.putNextEntry(zipEntry);
                FileInputStream fis = new FileInputStream(file);
                byte[] buffer = new byte[1024];
                int len;
                while ((len = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, len);
                }
                fis.close();
                zos.closeEntry();
            }
            zos.finish();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        // Retourner la réponse avec le fichier zip
        InputStreamResource isr = new InputStreamResource(new ByteArrayInputStream(baos.toByteArray()));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=fichiers.zip")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(isr);
    }



}
