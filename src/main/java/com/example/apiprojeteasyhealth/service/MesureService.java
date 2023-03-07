package com.example.apiprojeteasyhealth.service;

import com.example.apiprojeteasyhealth.dto.MesureDto;
import com.example.apiprojeteasyhealth.dto.MesureForPatient;
import com.example.apiprojeteasyhealth.dto.MesureForPatientAndPathologie;
import com.example.apiprojeteasyhealth.entity.Mesure;
import com.example.apiprojeteasyhealth.entity.Patient;
import com.example.apiprojeteasyhealth.entity.Suivi;
import com.example.apiprojeteasyhealth.repository.MesureRepository;
import com.example.apiprojeteasyhealth.repository.PatientRepository;
import com.example.apiprojeteasyhealth.repository.SuiviRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MesureService {

    @Autowired
    private MesureRepository mesureRepository;

    @Autowired
    private SuiviRepository suiviRepository;

    @Autowired
    PatientRepository patientRepository;

    public List<MesureForPatientAndPathologie> getMesureFromPatientAndPathologie(String mailPatient, String Pathologie, LocalDate starDate, LocalDate endDate){
        return mesureRepository.getMesureFromPatientAndPathologie(mailPatient, Pathologie, starDate, endDate);
    }

    public List<MesureForPatient> getMesureFromPatient(String mailPatient, LocalDate starDate, LocalDate endDate){
        return mesureRepository.getMesureFromPatient(mailPatient, starDate, endDate);
    }

    public Mesure addMesure(MesureDto mesureDto, String description, String mailPatient) {
        // Vérification de l'existence du patient
        Patient patient = patientRepository.findByAdresseMail(mailPatient).orElse(null);
        if (patient == null) {
            return null;
        }

        // Vérification de l'existence du suivi en cours pour le patient et la description donnée
        Suivi suivi = suiviRepository.findByConsultationPatientAdresseMailAndDescriptionAndEtat(patient.getAdresseMail(), description, "en cours");
        if (suivi == null) {
            return null;
        }

        // Création ou modification de la mesure
        Optional<Mesure> mesureOptional = mesureRepository.findBySuiviAndPeriode(suivi, mesureDto.getPeriode());
        if (mesureOptional.isPresent()) {
            Mesure existingMesure = mesureOptional.get();
            existingMesure.setValeur(mesureDto.getValeur());
            mesureRepository.save(existingMesure);
            return existingMesure;
        } else {
            Mesure mesure = new Mesure();
            mesure.setSuivi(suivi);
            mesure.setPatient(patient);
            mesure.setValeur(mesureDto.getValeur());
            mesure.setUnite(mesureDto.getUnite());
            mesure.setPeriode(mesureDto.getPeriode());
            mesureRepository.save(mesure);
            return mesure;
        }
    }
}


