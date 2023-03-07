package com.example.apiprojeteasyhealth.service;

import com.example.apiprojeteasyhealth.dto.AllAboutPatient;
import com.example.apiprojeteasyhealth.dto.PatientAlerte;
import com.example.apiprojeteasyhealth.dto.PatientSuiviDetailsDto;
import com.example.apiprojeteasyhealth.entity.Patient;
import com.example.apiprojeteasyhealth.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
