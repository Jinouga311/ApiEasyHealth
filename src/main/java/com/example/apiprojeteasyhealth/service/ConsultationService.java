package com.example.apiprojeteasyhealth.service;

import com.example.apiprojeteasyhealth.dto.ConsultationDetailedInformationsForMedecin;
import com.example.apiprojeteasyhealth.dto.ConsultationDto;
import com.example.apiprojeteasyhealth.dto.ConsultationInformationsForMedecin;
import com.example.apiprojeteasyhealth.dto.ConsultationInformationsForPatient;
import com.example.apiprojeteasyhealth.entity.*;
import com.example.apiprojeteasyhealth.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class ConsultationService {
    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    private RDVRepository rdvRepository;

    @Autowired
    private MedecinRepository medecinRepository;

    @Autowired
    private PathologieRepository pathologieRepository;

    public List<ConsultationInformationsForMedecin> getAllConsultationsByMedecinMail(String mail) {
        return consultationRepository.getAllConsultationsByMedecinMail(mail);
    }

    public List<ConsultationDetailedInformationsForMedecin> getAllDetailledConsultationsByMedecinMail(String mail){
        return consultationRepository.getAllDeatilledConsultationsByMedecinMail(mail);
    }

    public List<ConsultationInformationsForPatient> getConsultationsByPatientMail(String mail){
        return consultationRepository.getConsultationsInformationsForPatientByMail(mail);
    }

    public List<ConsultationInformationsForPatient>getConsultationsInformationsAvecSuiviForPatientByMail(String mail){
        return consultationRepository.getConsultationsInformationsAvecSuiviForPatientByMail(mail);
    }


    public Consultation addConsultation(ConsultationDto consultationDto, String mail) {
        // Vérification de l'existence du médecin
        Medecin medecin = medecinRepository.findByAdresseMail(mail).orElse(null);
        if (medecin == null) {
            return null;
        }

        // Vérification de l'existence du RDV pour le médecin et le patient à l'heure et à la date renseignées
        Optional<Patient> patient1 = patientRepository.findByAdresseMail(consultationDto.getPatientMail());
        RDV rdv = rdvRepository.findByMedecinAndPatientAndDateRDVAndHeureRDV(medecin, patient1, consultationDto.getDate(), LocalTime.parse((String) consultationDto.getHeure()));
        if (rdv == null) {
            return null;
        }

// Vérification de l'existence de la pathologie
        Pathologie pathologie = pathologieRepository.findByLibelle(consultationDto.getPathologie());
        if (pathologie == null) {
            pathologie = new Pathologie();
            pathologie.setLibelle(consultationDto.getPathologie());
            pathologieRepository.save(pathologie);
        }

// Récupération du patient associé au RDV
        Patient patient = rdv.getPatient();

// Vérification si une consultation similaire existe déjà pour le même médecin, patient et pathologie
        Consultation existingConsultation = consultationRepository.findByMedecinAndPatientAndPathologie(medecin, patient, pathologie);
        if (existingConsultation != null) {
            // Mise à jour des champs date et prix de la consultation existante avec les nouvelles valeurs renseignées
            existingConsultation.setDate(consultationDto.getDate());
            existingConsultation.setPrix(consultationDto.getPrix());
            consultationRepository.save(existingConsultation);
            return existingConsultation;
        }

// Création de la consultation
        Consultation consultation = new Consultation();
        consultation.setDate(consultationDto.getDate());
        consultation.setPrix(consultationDto.getPrix());
        consultation.setPathologie(pathologie);
        consultation.setMedecin(medecin);
        consultation.setPatient(patient);

        consultationRepository.save(consultation);

        return consultation;

    }
}