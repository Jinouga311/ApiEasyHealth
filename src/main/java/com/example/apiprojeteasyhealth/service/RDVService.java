package com.example.apiprojeteasyhealth.service;

import com.example.apiprojeteasyhealth.dto.RDVDTO;
import com.example.apiprojeteasyhealth.dto.RDVForMedecin;
import com.example.apiprojeteasyhealth.dto.RDVForPatient;
import com.example.apiprojeteasyhealth.dto.Role;
import com.example.apiprojeteasyhealth.entity.Medecin;
import com.example.apiprojeteasyhealth.entity.Patient;
import com.example.apiprojeteasyhealth.entity.RDV;
import com.example.apiprojeteasyhealth.repository.MedecinRepository;
import com.example.apiprojeteasyhealth.repository.PatientRepository;
import com.example.apiprojeteasyhealth.repository.RDVRepository;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class RDVService {

    @Autowired
    private RDVRepository rdvRepository;
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MedecinRepository medecinRepository;


    public List<RDVForPatient> getRDVByPatientMail(String mail){
        return rdvRepository.getRDVByPatientMail(mail);
    }

    public List<RDVForMedecin> getRDVByMedecinMail(String mail){
        return rdvRepository.getRDVByMedecinMail(mail);
    }

    public RDV createRDV(String dateRDV, String heureRDV, String heure, String mailDestinataireRDV, Role role, String mailEmetteurRDV) {
        LocalDate date = LocalDate.parse(dateRDV);
        LocalTime time = LocalTime.parse(heureRDV);
        LocalTime heureRDVTime = LocalTime.parse(heure);

        RDV rdv = new RDV();
        rdv.setDateRDV(date);
        rdv.setHeureRDV(time);
        rdv.setHeure(heureRDVTime);

        if (role == Role.MEDECIN) {
            Optional<Medecin> medecin = medecinRepository.findByAdresseMail(mailEmetteurRDV);
            if (!medecin.isPresent()) {
                throw new IllegalArgumentException("Médecin introuvable avec l'adresse mail: " + mailEmetteurRDV);
            }
            Optional<Patient> patient = patientRepository.findByAdresseMail(mailDestinataireRDV);
            if (!patient.isPresent()) {
                throw new IllegalArgumentException("Patient introuvable avec l'adresse mail: " + mailDestinataireRDV);
            }
            rdv.setMedecin(medecin.get());
            rdv.setPatient(patient.get());
        } else {
            Optional<Patient> patient = patientRepository.findByAdresseMail(mailEmetteurRDV);
            if (!patient.isPresent()) {
                throw new IllegalArgumentException("Patient introuvable avec l'adresse mail: " + mailEmetteurRDV);
            }
            Optional<Medecin> medecin = medecinRepository.findByAdresseMail(mailDestinataireRDV);
            if (!medecin.isPresent()) {
                throw new IllegalArgumentException("Médecin introuvable avec l'adresse mail: " + mailDestinataireRDV);
            }
            rdv.setPatient(patient.get());
            rdv.setMedecin(medecin.get());
        }

        return rdvRepository.save(rdv);
    }


    public RDV getRDVById(Long id) {
        Optional<RDV> rdv = rdvRepository.findById(id);
        if (rdv.isPresent()) {
            return rdv.get();
        } else {
            throw new IllegalArgumentException("RDV introuvable avec l'id: " + id);
        }
    }

}
