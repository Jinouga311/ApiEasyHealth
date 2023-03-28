package com.example.apiprojeteasyhealth.service;

import com.example.apiprojeteasyhealth.dto.AllAboutPatient;
import com.example.apiprojeteasyhealth.dto.PatientAlerte;
import com.example.apiprojeteasyhealth.dto.PatientSuiviDetailsDto;
import com.example.apiprojeteasyhealth.entity.Medecin;
import com.example.apiprojeteasyhealth.entity.Patient;
import com.example.apiprojeteasyhealth.exception.MedecinNotFoundException;
import com.example.apiprojeteasyhealth.exception.PatientNotFoundException;
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
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;


    public List<AllAboutPatient> getPatientInfoByEmail(String adresseMail) {
        return patientRepository.getPatientInfoByEmail(adresseMail);
    }

    public Optional<Patient> findByAdresseMail(String adresseMail) {
        return patientRepository.findByAdresseMail(adresseMail);
    }

    public List<PatientAlerte> getPatientAlertesByMedecinAndNbJours(String mail, int nbrJours){
        return patientRepository.getPatientAlertesByMedecinAndNbJours(mail, nbrJours);
    }

    public List<PatientSuiviDetailsDto> getPatientsSuiviDetailsByMedecinAdresseMail(String mail){
        return patientRepository.getPatientsSuiviDetailsByMedecinAdresseMail(mail);
    }

    public Patient updatePatient(String medecinMail, MultipartFile file, String adresseMail, String numeroTelephone, String pseudo, String motDePasse) {
        Patient patient = patientRepository.findByAdresseMail(medecinMail)
                .orElseThrow(() -> new PatientNotFoundException("Medecin not found with email: " + medecinMail));

        if (file != null && !file.isEmpty()) {
            // Supprimer l'ancienne photo de profil s'il y en avait une
            if (patient.getCheminFichier() != null && !patient.getCheminFichier().isEmpty()) {
                String oldFilePath = "C:/profilEasyHealth/patient/" + patient.getCheminFichier();
                File oldFile = new File(oldFilePath);
                oldFile.delete();
            }
            // Enregistrer la nouvelle photo de profil
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Path uploadDir = Paths.get("C:/profilEasyHealth/patient");
            Path filePath = uploadDir.resolve(fileName);
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException("Could not store file " + fileName + ". Please try again!", e);
            }
            // Mettre à jour le chemin d'accès à la photo de profil dans la base de données
            String fileFullPath = "C:/profilEasyHealth/patient/" + fileName;
            patient.setCheminFichier(fileFullPath);
        }

// Mettre à jour les autres informations du patient si elles sont fournies
        if (adresseMail != null) {
            patient.setAdresseMail(adresseMail);
        }
        if (numeroTelephone != null) {
            patient.setNumeroTelephone(numeroTelephone);
        }
        if (pseudo != null) {
            patient.setPseudo(pseudo);
        }
        if (motDePasse != null) {
            patient.setMotDePasse(motDePasse);
        }

        return patientRepository.save(patient);
    }


}
