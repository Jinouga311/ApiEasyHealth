package com.example.apiprojeteasyhealth.repository;

import com.example.apiprojeteasyhealth.entity.Consultation;
import com.example.apiprojeteasyhealth.entity.Patient;
import com.example.apiprojeteasyhealth.entity.RDV;
import com.example.apiprojeteasyhealth.entity.Suivi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuiviRepository extends JpaRepository<Suivi, Long> {

    public Suivi findByConsultationAndDescription(Consultation consultation, String description);

    Suivi findByConsultationPatientAdresseMailAndDescriptionAndEtat(String mailPatient, String description, String etat);


}
