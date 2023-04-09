package com.example.apiprojeteasyhealth.repository;

import com.example.apiprojeteasyhealth.dto.PatientPathologie;
import com.example.apiprojeteasyhealth.entity.Mesure;
import com.example.apiprojeteasyhealth.entity.Pathologie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PathologieRepository  extends JpaRepository<Pathologie, Long> {

    public Pathologie findByLibelle(String libelle);

    @Query("SELECT DISTINCT new com.example.apiprojeteasyhealth.dto.PatientPathologie(p.libelle) FROM Pathologie p JOIN p.consultations c JOIN c.patient pa JOIN c.medecin m WHERE pa.adresseMail = :adresseMail")
    List<PatientPathologie> findDistinctByPatientAdresseMail(@Param("adresseMail") String adresseMail);

}
