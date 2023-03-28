package com.example.apiprojeteasyhealth.service;

import com.example.apiprojeteasyhealth.entity.Medecin;
import com.example.apiprojeteasyhealth.entity.Patient;
import com.example.apiprojeteasyhealth.dto.Role;
import com.example.apiprojeteasyhealth.dto.UserRegistor;
import com.example.apiprojeteasyhealth.repository.MedecinRepository;
import com.example.apiprojeteasyhealth.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthService {

    private  PatientRepository patientRepository;
    private  MedecinRepository medecinRepository;

    private final Path uploadDir2;

    public AuthService(@Value("${upload.dir2}") String uploadDir2) {
        this.uploadDir2 = Paths.get(uploadDir2);
    }


    public void registerUser(MultipartFile file, UserRegistor userRegistor, Role role) throws IOException {
        // Vérifier si l'adresse mail n'est pas déjà utilisée
        System.out.println(userRegistor.getAdresseMail());
        if (patientRepository.findByAdresseMail(userRegistor.getAdresseMail()).isPresent() ||
                medecinRepository.findByAdresseMail(userRegistor.getAdresseMail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Adresse mail déjà utilisée");
        }

        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String roleDirectory = role.toString().toLowerCase();
        Path rolePath = uploadDir2.resolve(roleDirectory);
        Files.createDirectories(rolePath); // Crée le dossier si nécessaire

        String filePath = StringUtils.cleanPath(rolePath + "/" + filename);
        if (role.equals(Role.PATIENT)) {
            Patient patient = Patient.builder()
                    .nom(userRegistor.getNom())
                    .prenom(userRegistor.getPrenom())
                    .adresseMail(userRegistor.getAdresseMail())
                    .motDePasse(userRegistor.getMotDePasse())
                    .numeroTelephone(userRegistor.getNumeroTelephone())
                    .pseudo(userRegistor.getPseudo())
                    .cheminFichier(filePath)
                    .build();
            patientRepository.save(patient);
        } else if (role.equals(Role.MEDECIN)) {
            Medecin medecin = Medecin.builder()
                    .nom(userRegistor.getNom())
                    .adresseMail(userRegistor.getAdresseMail())
                    .motDePasse(userRegistor.getMotDePasse())
                    .numeroTelephone(userRegistor.getNumeroTelephone())
                    .pseudo(userRegistor.getPseudo())
                    .cheminFichier(filePath)
                    .build();
            medecinRepository.save(medecin);
        }
        Path targetPath = Paths.get(filePath);
        Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
    }





    public Optional<Patient> loginPatient(String adresseMail, String motDePasse) {
        Optional<Patient> patient = patientRepository.findByAdresseMail(adresseMail);
        if (patient.isPresent() && patient.get().getMotDePasse().equals(motDePasse)) {
            return patient;
        }
        return Optional.empty();
    }

    public Optional<Medecin> loginMedecin(String adresseMail, String motDePasse) {
        Optional<Medecin> medecin = medecinRepository.findByAdresseMail(adresseMail);
        if (medecin.isPresent() && medecin.get().getMotDePasse().equals(motDePasse)) {
            return medecin;
        }
        return Optional.empty();
    }
}
