package com.example.apiprojeteasyhealth.repository;


import com.example.apiprojeteasyhealth.dto.PatientAlerte;
import com.example.apiprojeteasyhealth.dto.PatientSuiviDetailsDto;
import com.example.apiprojeteasyhealth.entity.Patient;
import com.example.apiprojeteasyhealth.dto.AllAboutPatient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByAdresseMail(String adresse_mail);



    //Afficher les détails d'un patient :
    @Query("SELECT new com.example.apiprojeteasyhealth.dto.AllAboutPatient(p.nom, p.prenom, p.adresseMail, p.numeroTelephone, c.date, c.prix, pa.libelle, "
            + "m.nom, s.description, s.etat, m.adresseMail, m.numeroTelephone, o.dateOrdo, o.contenu, medicament.nom, medicament.description, pr.quantite) "
            + "FROM Patient p "
            + "LEFT JOIN p.consultations c "
            + "LEFT JOIN c.pathologie pa "
            + "LEFT JOIN c.medecin m "
            + "LEFT JOIN c.suivis s "
            + "LEFT JOIN c.ordonnances o "
            + "LEFT JOIN o.prescriptions pr "
            + "LEFT JOIN pr.medicament medicament "
            + "WHERE p.adresseMail = :mail")
    List<AllAboutPatient> getPatientInfoByEmail(@Param("mail") String adresseMail);

    //Affiche la liste des patients n'ayant pas vu un certain médécin dpuis de plus de X jours

    @Query("SELECT new com.example.apiprojeteasyhealth.dto.PatientAlerte(p.nom, p.prenom, p.adresseMail, p.numeroTelephone, MAX(c.date) ) " +
            "FROM Patient p " +
            "JOIN Consultation c ON p.id = c.patient.id " +
            "JOIN Medecin m ON c.medecin.adresseMail = m.adresseMail " +
            "WHERE m.adresseMail = :adresseMail AND DATEDIFF(NOW(), c.date) >= :nbJours " +
            "GROUP BY p.id, p.nom, p.prenom, p.adresseMail, p.numeroTelephone " +
            "ORDER BY MAX(c.date)  ASC")
    List<PatientAlerte> getPatientAlertesByMedecinAndNbJours(@Param("adresseMail") String adresseMail, @Param("nbJours") int nbJours);

    //Affiche en détails toutes les informations de chaque patient suivis par un médecin

    @Query("SELECT new com.example.apiprojeteasyhealth.dto.PatientSuiviDetailsDto("
            + "p.id, p.nom, p.prenom, p.adresseMail, p.numeroTelephone, s.id, s.description, s.etat, "
            + "m.id, m.nom, m.adresseMail, m.numeroTelephone, c.id, c.date, c.prix, pa.id, pa.libelle, "
            + "o.id, o.dateOrdo, o.contenu, pr.id, pr.quantite, md.id, md.nom, md.description, "
            + "mes.id, mes.valeur, mes.unite, mes.periode)"
            + "FROM Patient p "
            + "INNER JOIN Consultation c ON p.id = c.patient.id "
            + "INNER JOIN Suivi s ON c.id = s.consultation.id "
            + "LEFT JOIN Mesure mes ON s.id = mes.suivi.id "
            + "LEFT JOIN Ordonnance o ON c.id = o.consultation.id "
            + "LEFT JOIN Prescription pr ON o.id = pr.ordonnance.id "
            + "LEFT JOIN Medicament md ON pr.medicament.id = md.id "
            + "INNER JOIN Medecin m ON m.id = c.medecin.id "
            + "INNER JOIN Pathologie pa ON c.pathologie.id = pa.id "
            + "WHERE m.adresseMail = :mail "
            + "ORDER BY p.id, s.id, mes.id")
    List<PatientSuiviDetailsDto> getPatientsSuiviDetailsByMedecinAdresseMail(@Param("mail") String adresseMail);



}