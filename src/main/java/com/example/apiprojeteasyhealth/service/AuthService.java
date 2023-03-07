package com.example.apiprojeteasyhealth.service;

import com.example.apiprojeteasyhealth.entity.Medecin;
import com.example.apiprojeteasyhealth.entity.Patient;
import com.example.apiprojeteasyhealth.dto.Role;
import com.example.apiprojeteasyhealth.dto.UserRegistor;
import com.example.apiprojeteasyhealth.repository.MedecinRepository;
import com.example.apiprojeteasyhealth.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class AuthService {

    private final PatientRepository patientRepository;
    private final MedecinRepository medecinRepository;


    public void registerUser(UserRegistor userRegistor) {

        // Vérifier si l'adresse mail n'est pas déjà utilisée
        if (patientRepository.findByAdresseMail(userRegistor.getAdresseMail()).isPresent() ||
                medecinRepository.findByAdresseMail(userRegistor.getAdresseMail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Adresse mail déjà utilisée");
        }
        // Enregistrer le patient ou le médecin dans la base de données
        if (userRegistor.getRole().equals(Role.PATIENT)) {
            Patient patient = Patient.builder()
                    .nom(userRegistor.getNom())
                    .prenom(userRegistor.getPrenom())
                    .adresseMail(userRegistor.getAdresseMail())
                    .motDePasse(userRegistor.getMotDePasse())
                    .numeroTelephone(userRegistor.getNumeroTelephone())
                    .pseudo(userRegistor.getPseudo())
                    .build();
            patientRepository.save(patient);
        } else if (userRegistor.getRole().equals(Role.MEDECIN)) {
            Medecin medecin = Medecin.builder()
                    .nom(userRegistor.getNom())
                    .adresseMail(userRegistor.getAdresseMail())
                    .motDePasse(userRegistor.getMotDePasse())
                    .numeroTelephone(userRegistor.getNumeroTelephone())
                    .pseudo(userRegistor.getPseudo())
                    .build();
            medecinRepository.save(medecin);
        }
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
