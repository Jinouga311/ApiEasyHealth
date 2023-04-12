package com.example.apiprojeteasyhealth.repository;

import com.example.apiprojeteasyhealth.dto.MesureForPatient;
import com.example.apiprojeteasyhealth.dto.MesureForPatientAndPathologie;
import com.example.apiprojeteasyhealth.entity.Mesure;
import com.example.apiprojeteasyhealth.entity.Suivi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MesureRepository extends JpaRepository<Mesure, Long> {

    //Affiche les dernières mesures d'un patient pour une pathologie donnée
    @Query("SELECT new com.example.apiprojeteasyhealth.dto.MesureForPatientAndPathologie(m.valeur, m.unite, m.periode, m.dateMesure, m.nomMesure, pa.libelle) "
            + "FROM Mesure m "
            + "JOIN m.suivi s "
            + "JOIN s.consultation c "
            + "JOIN c.pathologie pa "
            + "WHERE m.patient.adresseMail = :mailPatient AND pa.libelle = :libellePathologie AND m.dateMesure BETWEEN :startDate AND :endDate "
            + "ORDER BY m.periode DESC")
    List<MesureForPatientAndPathologie> getMesureFromPatientAndPathologie(@Param("mailPatient") String mailPatient, @Param("libellePathologie") String libellePathologie, @Param("startDate")LocalDate starDate, @Param("endDate") LocalDate endDate);

    //Affiche les dernières mesures d'un patient donnée
    @Query("SELECT new com.example.apiprojeteasyhealth.dto.MesureForPatient(m.valeur, m.unite, m.periode, m.dateMesure, pa.libelle)\n" +
            "FROM Mesure m\n" +
            "JOIN m.suivi s\n" +
            "JOIN s.consultation c\n" +
            "JOIN c.pathologie pa\n" +
            "WHERE m.patient.adresseMail = :mailPatient\n" +
            "AND m.dateMesure BETWEEN :startDate AND :endDate\n" +
            "ORDER BY m.periode DESC")
    List<MesureForPatient> getMesureFromPatient(@Param("mailPatient") String mailPatient, @Param("startDate")LocalDate startDate, @Param("endDate") LocalDate endDate);



    Optional<Mesure> findBySuiviAndPeriode(Suivi suivi, String periode);

    Optional<Mesure> findBySuiviAndDateMesureAndPeriodeAndNomMesure(@Param("suivi") Suivi suivi,
                                                        @Param("dateMesure") LocalDate dateMesure,
                                                        @Param("periode") String periode, @Param("nomMesure") String nomMesure);



}