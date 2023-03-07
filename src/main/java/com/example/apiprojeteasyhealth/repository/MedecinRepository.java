package com.example.apiprojeteasyhealth.repository;


import com.example.apiprojeteasyhealth.entity.Consultation;
import com.example.apiprojeteasyhealth.entity.Medecin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedecinRepository extends JpaRepository<Medecin, Long> {
    Optional<Medecin> findByAdresseMail(String adresse_mail);
}
