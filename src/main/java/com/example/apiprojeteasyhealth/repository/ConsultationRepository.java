package com.example.apiprojeteasyhealth.repository;

import com.example.apiprojeteasyhealth.dto.*;
import com.example.apiprojeteasyhealth.entity.Consultation;
import com.example.apiprojeteasyhealth.entity.Medecin;
import com.example.apiprojeteasyhealth.entity.Pathologie;
import com.example.apiprojeteasyhealth.entity.Patient;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

    //Afficher la liste des patients suivis par un médecin :
    @Query("SELECT new com.example.apiprojeteasyhealth.dto.ConsultationInformationsForMedecin(p.nom, p.prenom, p.adresseMail, p.numeroTelephone, c.date, c.prix, pa.libelle) "
            + "FROM Patient p "
            + "JOIN p.consultations c "
            + "JOIN c.pathologie pa "
            + "WHERE c.medecin.adresseMail = :mail "
            + "ORDER BY c.date DESC")
    List<ConsultationInformationsForMedecin> getAllConsultationsByMedecinMail(@Param("mail") String mail);


    @Query("SELECT new com.example.apiprojeteasyhealth.dto.ConsultationDetailedInformationsForMedecin(m.adresseMail, p.nom, p.prenom, p.adresseMail, p.numeroTelephone, c.date, c.prix, pa.libelle, m.nom, s.description, s.etat, o.dateOrdo, o.contenu, med.nom, med.description, pr.quantite) "
            + "FROM Patient p "
            + "JOIN Consultation c ON p.id = c.patient.id "
            + "JOIN Pathologie pa ON c.pathologie.id = pa.id "
            + "JOIN Medecin m ON c.medecin.id = m.id "
            + "LEFT JOIN Suivi s ON c.id = s.consultation.id "
            + "LEFT JOIN Ordonnance o ON c.id = o.consultation.id "
            + "LEFT JOIN Prescription pr ON o.id = pr.ordonnance.id "
            + "LEFT JOIN Medicament med ON pr.medicament.id = med.id "
            + "WHERE m.adresseMail = :mail "
            + "ORDER BY pr.quantite ASC")
    List<ConsultationDetailedInformationsForMedecin> getAllDeatilledConsultationsByMedecinMail(@Param("mail") String mail);

    //Afiche la liste des consultations pour un patient donnée
    @Query("SELECT c.id as idConsultation, c.date as date, c.prix as prix, pa.libelle as pathologieLibelle, m.nom as medecinNom, "
            + "s.description as suiviDescription, s.etat as suiviEtat, o.dateOrdo as ordonnanceDate, "
            + "med.nom as medicamentNom, med.description as medicamentDescription, pr.quantite as prescriptionQuantite "
            + "FROM Consultation c "
            + "JOIN c.pathologie pa "
            + "JOIN c.medecin m "
            + "JOIN c.patient p "
            + "LEFT JOIN c.suivis s "
            + "LEFT JOIN c.ordonnances o "
            + "LEFT JOIN o.prescriptions pr "
            + "LEFT JOIN pr.medicament med "
            + "WHERE p.adresseMail = :mail "
            + "ORDER BY c.date DESC")
    List<Tuple> findConsultationsByPatientMail(@Param("mail") String adresseMail);

    public default List<ConsultationInformationsForPatient> getConsultationsByPatientMail(String adresseMail) {
        List<Tuple> rawData = findConsultationsByPatientMail(adresseMail);
        Map<Long, ConsultationInformationsForPatient> consultationMap = new LinkedHashMap<>();

        for (Tuple row : rawData) {
            Long idConsultation = row.get("idConsultation", Long.class);
            ConsultationInformationsForPatient consultation = consultationMap.computeIfAbsent(idConsultation, k -> {
                ConsultationInformationsForPatient newConsultation = new ConsultationInformationsForPatient(
                        idConsultation,
                        row.get("date", LocalDate.class),
                        row.get("prix", Float.class),
                        row.get("pathologieLibelle", String.class),
                        row.get("medecinNom", String.class),
                        row.get("suiviDescription", String.class),
                        row.get("suiviEtat", String.class),
                        new OrdonnanceDto(row.get("ordonnanceDate", LocalDate.class), new ArrayList<>())
                );
                return newConsultation;
            });

            PrescriptionDto prescription = new PrescriptionDto(
                    row.get("medicamentNom", String.class),
                    row.get("medicamentDescription", String.class),
                    row.get("prescriptionQuantite", Integer.class)
            );

            if (prescription.getMedicamentNom() != null) {
                consultation.getOrdonnance().getPrescriptions().add(prescription);
            }
        }

        return new ArrayList<>(consultationMap.values());
    }


    //Afiche la liste des consultations avec suivi pour un patient donnée
    @Query("SELECT c.id as idConsultation, c.date as date, c.prix as prix, pa.libelle as pathologieLibelle, m.nom as medecinNom, "
            + "s.description as suiviDescription, s.etat as suiviEtat, o.dateOrdo as ordonnanceDate, o.contenu as ordonnanceContenu, "
            + "medicament.nom as medicamentNom, medicament.description as medicamentDescription, pr.quantite as prescriptionQuantite "
            + "FROM Consultation c "
            + "JOIN c.pathologie pa "
            + "JOIN c.medecin m "
            + "LEFT JOIN c.suivis s "
            + "LEFT JOIN c.ordonnances o "
            + "LEFT JOIN o.prescriptions pr "
            + "LEFT JOIN pr.medicament medicament "
            + "JOIN c.patient p "
            + "WHERE p.adresseMail = :adresseMail AND (s.id IS NOT NULL OR o.id IS NOT NULL OR pr.id IS NOT NULL) "
            + "ORDER BY c.date DESC")
    List<Tuple> findConsultationsWithSuiviByPatientEmail(@Param("adresseMail") String adresseMail);

    public default List<ConsultationInformationsForPatient> getConsultationsWithSuiviByPatientEmail(String adresseMail) {
        List<Tuple> rawData = findConsultationsWithSuiviByPatientEmail(adresseMail);
        Map<Long, ConsultationInformationsForPatient> consultationMap = new LinkedHashMap<>();

        for (Tuple row : rawData) {
            Long idConsultation = row.get("idConsultation", Long.class);
            ConsultationInformationsForPatient consultation = consultationMap.computeIfAbsent(idConsultation, k -> {
                ConsultationInformationsForPatient newConsultation = new ConsultationInformationsForPatient(
                        idConsultation,
                        row.get("date", LocalDate.class),
                        row.get("prix", Float.class),
                        row.get("pathologieLibelle", String.class),
                        row.get("medecinNom", String.class),
                        row.get("suiviDescription", String.class),
                        row.get("suiviEtat", String.class),
                        new OrdonnanceDto(row.get("ordonnanceDate", LocalDate.class), new ArrayList<>())
                );
                return newConsultation;
            });

            PrescriptionDto prescription = new PrescriptionDto(
                    row.get("medicamentNom", String.class),
                    row.get("medicamentDescription", String.class),
                    row.get("prescriptionQuantite", Integer.class)
            );

            if (prescription.getMedicamentNom() != null) {
                consultation.getOrdonnance().getPrescriptions().add(prescription);
            }
        }

        return new ArrayList<>(consultationMap.values());
    }


    Consultation findByMedecinAndPatientAndPathologie(Medecin medecin, Patient patient, Pathologie pathologie);





}
