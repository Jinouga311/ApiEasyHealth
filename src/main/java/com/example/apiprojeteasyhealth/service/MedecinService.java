package com.example.apiprojeteasyhealth.service;

import com.example.apiprojeteasyhealth.dto.AllAboutMedecin;
import com.example.apiprojeteasyhealth.dto.AllAboutPatient;
import com.example.apiprojeteasyhealth.entity.Medecin;
import com.example.apiprojeteasyhealth.exception.MedecinNotFoundException;
import com.example.apiprojeteasyhealth.repository.MedecinRepository;
import com.example.apiprojeteasyhealth.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class MedecinService {

    @Autowired
    private MedecinRepository medecinRepository;

    public List<AllAboutMedecin> getMedecinInfoByEmail(String adresseMail) {
        return medecinRepository.getMedecinInfoByEmail(adresseMail);
    }

    public List<AllAboutMedecin> getAllMedecinsInfo() {
        return medecinRepository.getAllMedecinsInfo();
    }




    public Medecin updateMedecin(String medecinMail, MultipartFile file, String adresseMail, String numeroTelephone, String pseudo, String motDePasse) throws IOException {
        Medecin medecin = medecinRepository.findByAdresseMail(medecinMail)
                .orElseThrow(() -> new MedecinNotFoundException("Medecin not found with email: " + medecinMail));

        if (file != null && !file.isEmpty()) {
            // Supprimer l'ancienne photo de profil s'il y en avait une
            if (medecin.getCheminFichier() != null && !medecin.getCheminFichier().isEmpty()) {
                Path oldFilePath = Paths.get(medecin.getCheminFichier());
                Files.deleteIfExists(oldFilePath);
            }
            // Enregistrer la nouvelle photo de profil
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Path uploadDir = Paths.get("profilEasyHealth", "medecin").toAbsolutePath().normalize();
            Files.createDirectories(uploadDir);
            Path filePath = uploadDir.resolve(fileName);
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException("Could not store file " + fileName + ". Please try again!", e);
            }
            // Mettre à jour le chemin d'accès à la photo de profil dans la base de données
            String fileFullPath = filePath.toString();
            medecin.setCheminFichier(fileFullPath);
        }

        // Mettre à jour les autres informations du médecin si elles sont fournies
        if (adresseMail != null) {
            medecin.setAdresseMail(adresseMail);
        }
        if (numeroTelephone != null) {
            medecin.setNumeroTelephone(numeroTelephone);
        }
        if (pseudo != null) {
            medecin.setPseudo(pseudo);
        }
        if (motDePasse != null) {
            medecin.setMotDePasse(motDePasse);
        }

        return medecinRepository.save(medecin);
    }



}

