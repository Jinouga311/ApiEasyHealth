package com.example.apiprojeteasyhealth.repository;

import com.example.apiprojeteasyhealth.dto.RDVForMedecin;
import com.example.apiprojeteasyhealth.dto.RDVForPatient;
import com.example.apiprojeteasyhealth.entity.Medecin;
import com.example.apiprojeteasyhealth.entity.Patient;
import com.example.apiprojeteasyhealth.entity.RDV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface RDVRepository extends JpaRepository<RDV, Long> {

    @Query("SELECT new com.example.apiprojeteasyhealth.dto.RDVForPatient(r.dateRDV, r.heureRDV, r.duree, m.nom, m.adresseMail, m.numeroTelephone) "
            + "FROM RDV r "
            + "JOIN r.medecin m "
            + "WHERE r.patient.adresseMail = :mail "
            + "ORDER BY r.dateRDV ASC")
    List<RDVForPatient> getRDVByPatientMail(@Param("mail") String mail);
    @Query("SELECT new com.example.apiprojeteasyhealth.dto.RDVForMedecin(r.dateRDV, r.heureRDV, r.duree, p.nom, p.adresseMail, p.numeroTelephone) "
            + "FROM RDV r "
            + "JOIN r.patient p "
            + "WHERE r.medecin.adresseMail = :mail "
            + "ORDER BY r.dateRDV ASC")
    List<RDVForMedecin> getRDVByMedecinMail(@Param("mail") String mail);

    @Query("SELECT rdv.id FROM RDV rdv WHERE rdv.dateRDV = :dateRDV AND rdv.heureRDV = :heureRDV  AND rdv.medecin.adresseMail = :mailDestinataireRDV")
    Long findIdByDateRDVAndHeureRDVAndDureeAndMedecinAdresseMail(@Param("dateRDV") LocalDate dateRDV, @Param("heureRDV") LocalTime heureRDV, @Param("mailDestinataireRDV") String mailDestinataireRDV);


    RDV findByMedecinAndPatientAndDateRDVAndHeureRDV(Medecin medecin, Optional<Patient> patient, LocalDate dateRDV, LocalTime heureRDV);

    List<RDV> findAllByMedecinAdresseMailAndDateRDV(String medecinMail, LocalDate date);




}
