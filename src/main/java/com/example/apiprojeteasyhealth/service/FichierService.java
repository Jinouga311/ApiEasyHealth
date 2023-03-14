package com.example.apiprojeteasyhealth.service;

import com.example.apiprojeteasyhealth.entity.Fichier;
import com.example.apiprojeteasyhealth.entity.Medecin;
import com.example.apiprojeteasyhealth.entity.Patient;
import com.example.apiprojeteasyhealth.repository.FichierRepository;
import com.example.apiprojeteasyhealth.repository.MedecinRepository;
import com.example.apiprojeteasyhealth.repository.PatientRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class FichierService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MedecinRepository medecinRepository;

    @Autowired
    private FichierRepository fichierRepository;

    private final Path uploadDir;

    public FichierService(@Value("${upload.dir}") String uploadDir) {
        this.uploadDir = Paths.get(uploadDir);
    }


    public void uploadFile(MultipartFile file, String mailPatient, String mailMedecin) throws IOException {
        // Recherche du patient
        Optional<Patient> optionalPatient = patientRepository.findByAdresseMail(mailPatient);
        Patient patient = optionalPatient.orElseThrow(() -> new IllegalArgumentException("Patient not found with email : " + mailPatient));

        // Recherche du médecin
        Optional<Medecin> optionalMedecin = medecinRepository.findByAdresseMail(mailMedecin);
        Medecin medecin = optionalMedecin.orElseThrow(() -> new IllegalArgumentException("Medecin not found with email : " + mailMedecin));

        // Création d'un objet Fichier avec les ids du patient et du médecin
        Fichier fichier = new Fichier();
        fichier.setNomFichier(file.getOriginalFilename());
        fichier.setTypeFichier(file.getContentType());
        fichier.setPatient(patient);
        fichier.setMedecin(medecin);

        // Enregistrement du fichier en base de données
        fichierRepository.save(fichier);

        // Sauvegarde du fichier sur le disque dur
        Path path = this.uploadDir.resolve(fichier.getNomFichier() + "." + FilenameUtils.getExtension(file.getOriginalFilename()));
        Files.createDirectories(path.getParent());
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        // Mise à jour du chemin du fichier en base de données
        fichier.setCheminFichier(path.toAbsolutePath().toString());
        fichierRepository.save(fichier);
    }

    public List<Fichier> getFilesByPatientAndMedecin(String mailPatient, String mailMedecin) {
// Recherche du patient
        Optional<Patient> optionalPatient = patientRepository.findByAdresseMail(mailPatient);
        Patient patient = optionalPatient.orElseThrow(() -> new IllegalArgumentException("Patient not found with email : " + mailPatient));

        // Recherche du médecin
        Optional<Medecin> optionalMedecin = medecinRepository.findByAdresseMail(mailMedecin);
        Medecin medecin = optionalMedecin.orElseThrow(() -> new IllegalArgumentException("Medecin not found with email : " + mailMedecin));

        return fichierRepository.findByPatientAndMedecin(patient, medecin);
    }

}