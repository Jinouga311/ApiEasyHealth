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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthService(Path uploadDir2) {
        this.uploadDir2 = uploadDir2;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }




    public AuthService(@Value("${upload.dir2}") String uploadDir2, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.uploadDir2 = Paths.get(uploadDir2);
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
        Path rolePath = Paths.get("profilEasyHealth", roleDirectory);
        Files.createDirectories(rolePath); // Crée le dossier si nécessaire

        String hashedPassword = bCryptPasswordEncoder.encode(userRegistor.getMotDePasse());

        Path filePath = rolePath.resolve(filename);
        if (role.equals(Role.PATIENT)) {
            Patient patient = Patient.builder()
                    .nom(userRegistor.getNom())
                    .prenom(userRegistor.getPrenom())
                    .adresseMail(userRegistor.getAdresseMail())
                    .motDePasse(hashedPassword)
                    .numeroTelephone(userRegistor.getNumeroTelephone())
                    .pseudo(userRegistor.getPseudo())
                    .cheminFichier(filePath.toString())
                    .build();
            patientRepository.save(patient);
        } else if (role.equals(Role.MEDECIN)) {
            Medecin medecin = Medecin.builder()
                    .nom(userRegistor.getNom())
                    .adresseMail(userRegistor.getAdresseMail())
                    .motDePasse(hashedPassword)
                    .numeroTelephone(userRegistor.getNumeroTelephone())
                    .pseudo(userRegistor.getPseudo())
                    .cheminFichier(filePath.toString())
                    .build();
            medecinRepository.save(medecin);
        }
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
    }






    public Optional<Patient> loginPatient(String adresseMail, String motDePasse) {
        Optional<Patient> patient = patientRepository.findByAdresseMail(adresseMail);
        if (patient.isPresent() && bCryptPasswordEncoder.matches(motDePasse, patient.get().getMotDePasse())) {
            return patient;
        }
        return Optional.empty();
    }

    public Optional<Medecin> loginMedecin(String adresseMail, String motDePasse) {
        Optional<Medecin> medecin = medecinRepository.findByAdresseMail(adresseMail);
        if (medecin.isPresent() && bCryptPasswordEncoder.matches(motDePasse, medecin.get().getMotDePasse())) {
            return medecin;
        }
        return Optional.empty();
    }

}
