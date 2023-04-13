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
        List<MesureForPatientAndPathologie> mesures =  mesureRepository.getMesureFromPatientAndPathologie(mailPatient, Pathologie, starDate, endDate);

        Long idMesure = 1L;
        for (MesureForPatientAndPathologie mesure : mesures) {
            mesure.setIdMesure(idMesure++);
        }

        return mesures;
    }

    public List<MesureForPatient> getMesureFromPatient(String mailPatient, LocalDate starDate, LocalDate endDate){
        List<MesureForPatient> mesures = mesureRepository.getMesureFromPatient(mailPatient, starDate, endDate);

        Long idMesure = 1L;
        for (MesureForPatient mesure : mesures) {
            mesure.setIdMesure(idMesure++);
        }

        return mesures;
    }

    public Mesure addMesure(MesureDto mesureDto, String description, String mailPatient) {
        // Vérification de l'existence du patient
        Patient patient = patientRepository.findByAdresseMail(mailPatient).orElse(null);
        if (patient == null) {
            throw new IllegalArgumentException("Patient not found.");
        }

        // Vérification de l'existence du suivi en cours pour le patient et la description donnée
        Suivi suivi = suiviRepository.findByConsultationPatientAdresseMailAndDescriptionAndEtat(patient.getAdresseMail(), description, "en cours");
        if (suivi == null) {
            throw new IllegalArgumentException("Suivi not found.");
        }

        // Vérification de l'existence de la mesure pour le suivi, la date et la période données
        Optional<Mesure> mesureOptional = mesureRepository.findBySuiviAndDateMesureAndPeriodeAndNomMesure(suivi, mesureDto.getDateMesure(), mesureDto.getPeriode(), mesureDto.getNomMesure());
        if (mesureOptional.isPresent()) {
            throw new IllegalArgumentException("Mesure already exists for the given date and period.");
        }

        // Création de la mesure
        Mesure mesure = new Mesure();
        mesure.setSuivi(suivi);
        mesure.setPatient(patient);
        mesure.setValeur(mesureDto.getValeur());
        mesure.setUnite(mesureDto.getUnite());
        mesure.setPeriode(mesureDto.getPeriode());
        mesure.setDateMesure(mesureDto.getDateMesure());
        mesure.setNomMesure(mesureDto.getNomMesure());
        mesureRepository.save(mesure);
        return mesure;
    }

}


