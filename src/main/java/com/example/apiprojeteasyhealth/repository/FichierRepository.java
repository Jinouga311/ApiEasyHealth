package com.example.apiprojeteasyhealth.repository;

import com.example.apiprojeteasyhealth.entity.Fichier;
import com.example.apiprojeteasyhealth.entity.Medecin;
import com.example.apiprojeteasyhealth.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FichierRepository  extends JpaRepository<Fichier, Long> {
    List<Fichier> findByPatientAndMedecin(Patient patient, Medecin medecin);
    Optional<Fichier> findByNomFichier(String nomFichier);
}
