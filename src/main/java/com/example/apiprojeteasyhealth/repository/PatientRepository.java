package com.example.apiprojeteasyhealth.repository;


import com.example.apiprojeteasyhealth.dto.*;
import com.example.apiprojeteasyhealth.entity.Patient;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByAdresseMail(String adresse_mail);



    //Afficher les détails d'un patient :
    @Query("SELECT p.id as idPatient, p.nom as nom, p.prenom as prenom, p.adresseMail as adresseMail, p.numeroTelephone as numeroTelephone, p.cheminFichier as cheminFichier, "
            + "c.id as idConsultation, c.date as date, c.prix as prix, pa.libelle as pathologieLibelle, m.nom as medecinNom, "
            + "s.description as suiviDescription, s.etat as suiviEtat, o.dateOrdo as ordonnanceDate, o.contenu as ordonnanceContenu, "
            + "medicament.nom as medicamentNom, medicament.description as medicamentDescription, pr.quantite as prescriptionQuantite "
            + "FROM Patient p "
            + "LEFT JOIN p.consultations c "
            + "LEFT JOIN c.pathologie pa "
            + "LEFT JOIN c.medecin m "
            + "LEFT JOIN c.suivis s "
            + "LEFT JOIN c.ordonnances o "
            + "LEFT JOIN o.prescriptions pr "
            + "LEFT JOIN pr.medicament medicament "
            + "WHERE p.adresseMail = :mail")
    List<Tuple> findPatientInfoByEmail(@Param("mail") String adresseMail);

    public default List<AllAboutPatient> getPatientInfoByEmail(String adresseMail) {
        List<Tuple> rawData = findPatientInfoByEmail(adresseMail);
        Map<Long, AllAboutPatient> resultMap = new LinkedHashMap<>();
        Map<Long, Long> consultationIdMap = new HashMap<>();
        long genericConsultationId = 1;

        for (Tuple row : rawData) {
            Long idPatient = row.get("idPatient", Long.class);
            AllAboutPatient patient = resultMap.computeIfAbsent(idPatient, k -> {
                long idPatientInfo = resultMap.size() + 1;
                return new AllAboutPatient(
                        idPatientInfo,
                        row.get("nom", String.class),
                        row.get("prenom", String.class),
                        row.get("adresseMail", String.class),
                        row.get("numeroTelephone", String.class),
                        row.get("cheminFichier", String.class),
                        new ArrayList<>()
                );
            });

            Long idConsultation = row.get("idConsultation", Long.class);
            if (idConsultation != null && !consultationIdMap.containsKey(idConsultation)) {
                consultationIdMap.put(idConsultation, genericConsultationId);
                ConsultationInformationsForPatient consultation = new ConsultationInformationsForPatient(
                        genericConsultationId++,
                        row.get("date", LocalDate.class),
                        row.get("prix", Float.class),
                        row.get("pathologieLibelle", String.class),
                        row.get("medecinNom", String.class),
                        row.get("suiviDescription", String.class),
                        row.get("suiviEtat", String.class),
                        row.get("ordonnanceDate", LocalDate.class),
                        row.get("ordonnanceContenu", String.class),
                        row.get("medicamentNom", String.class),
                        row.get("medicamentDescription", String.class),
                        row.get("prescriptionQuantite", Integer.class)
                );
                patient.getConsultations().add(consultation);
            }
        }

        return new ArrayList<>(resultMap.values());
    }


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

    @Query("SELECT p.id as idPatient, p.nom as nom, p.prenom as prenom, p.adresseMail as adresseMail, p.numeroTelephone as numeroTelephone, m.adresseMail as adresseMailMedecin, m.nom as nomMedecin, p.cheminFichier as photoProfil, "
            + "s.description as description, s.etat as etat "
            + "FROM Patient p "
            + "INNER JOIN Consultation c ON p.id = c.patient.id "
            + "INNER JOIN Suivi s ON c.id = s.consultation.id "
            + "INNER JOIN Medecin m ON m.id = c.medecin.id "
            + "WHERE m.adresseMail = :mail "
            + "ORDER BY p.id, s.id")
    List<Tuple> findPatientSuiviDataByMedecinAdresseMail(@Param("mail") String adresseMail);

    public default List<PatientSuiviDetailsDto> getPatientsSuiviDetailsByMedecinAdresseMail(String adresseMail) {
        List<Tuple> rawData = findPatientSuiviDataByMedecinAdresseMail(adresseMail);

        Map<Long, PatientSuiviDetailsDto> resultMap = new LinkedHashMap<>();
        AtomicInteger patientIdCounter = new AtomicInteger(1);
        for (Tuple row : rawData) {
            Long idPatient = row.get("idPatient", Long.class);
            PatientSuiviDetailsDto patient = resultMap.computeIfAbsent(idPatient, k -> {
                int newPatientId = patientIdCounter.getAndIncrement();
                return new PatientSuiviDetailsDto(
                        (long) newPatientId,
                        row.get("nom", String.class),
                        row.get("prenom", String.class),
                        row.get("photoProfil", String.class),
                        row.get("adresseMail", String.class),
                        row.get("numeroTelephone", String.class),
                        row.get("adresseMailMedecin", String.class),
                        row.get("nomMedecin", String.class),
                        new ArrayList<>()
                );
            });

            int suiviId = patient.getSuivis().size() + 1;
            SuiviDto suivi = new SuiviDto(
                    suiviId,
                    row.get("description", String.class),
                    row.get("etat", String.class)
            );
            patient.getSuivis().add(suivi);
        }

        return new ArrayList<>(resultMap.values());
    }









}