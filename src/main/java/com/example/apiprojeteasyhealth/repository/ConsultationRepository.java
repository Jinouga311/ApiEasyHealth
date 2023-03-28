package com.example.apiprojeteasyhealth.repository;

import com.example.apiprojeteasyhealth.dto.ConsultationDetailedInformationsForMedecin;
import com.example.apiprojeteasyhealth.dto.ConsultationInformationsForPatient;
import com.example.apiprojeteasyhealth.entity.Consultation;
import com.example.apiprojeteasyhealth.dto.ConsultationInformationsForMedecin;
import com.example.apiprojeteasyhealth.entity.Medecin;
import com.example.apiprojeteasyhealth.entity.Pathologie;
import com.example.apiprojeteasyhealth.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

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
    @Query("SELECT new com.example.apiprojeteasyhealth.dto.ConsultationInformationsForPatient(c.id, c.date, c.prix, pa.libelle, m.nom, s.description, s.etat, o.dateOrdo, o.contenu, med.nom, med.description, pr.quantite) \n" +
            "FROM Consultation c \n" +
            "JOIN Pathologie pa ON c.pathologie.id = pa.id \n" +
            "JOIN Medecin m ON c.medecin.id = m.id \n" +
            "JOIN Patient p ON c.patient.id = p.id \n" +
            "JOIN Ordonnance o ON c.id = o.consultation.id \n" +
            "JOIN Prescription pr ON o.id = pr.ordonnance.id \n" +
            "JOIN Medicament med ON pr.medicament.id = med.id \n" +
            "LEFT JOIN Suivi s ON c.id = s.consultation.id \n" +
            "WHERE p.adresseMail = :adresseMail \n" +
            "ORDER BY c.date DESC\n")
    List<ConsultationInformationsForPatient> getConsultationsInformationsForPatientByMail(@Param("adresseMail") String adresseMail);

    //Afiche la liste des consultations avec suivi pour un patient donnée
    @Query("SELECT new com.example.apiprojeteasyhealth.dto.ConsultationInformationsForPatient(c.id, c.date, c.prix, pa.libelle, m.nom, s.description, s.etat, o.dateOrdo, o.contenu, med.nom, med.description, pr.quantite) " +
            "FROM Consultation c " +
            "JOIN Pathologie pa ON c.pathologie.id = pa.id " +
            "JOIN Medecin m ON c.medecin.id = m.id " +
            "LEFT JOIN Suivi s ON c.id = s.consultation.id " +
            "LEFT JOIN Ordonnance o ON c.id = o.consultation.id " +
            "LEFT JOIN Prescription pr ON o.id = pr.ordonnance.id " +
            "LEFT JOIN Medicament med ON pr.medicament.id = med.id " +
            "JOIN Patient p ON c.patient.id = p.id " +
            "WHERE p.adresseMail = :adresseMail AND (s.id IS NOT NULL OR o.id IS NOT NULL OR pr.id IS NOT NULL) " +
            "ORDER BY c.date DESC")
    List<ConsultationInformationsForPatient> getConsultationsInformationsAvecSuiviForPatientByMail(@Param("adresseMail") String adresseMail);

    Consultation findByMedecinAndPatientAndPathologie(Medecin medecin, Patient patient, Pathologie pathologie);





}
