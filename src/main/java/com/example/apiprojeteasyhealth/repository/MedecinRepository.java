package com.example.apiprojeteasyhealth.repository;


import com.example.apiprojeteasyhealth.dto.AllAboutMedecin;
import com.example.apiprojeteasyhealth.dto.AllAboutPatient;
import com.example.apiprojeteasyhealth.dto.ConsultationInformationsForMedecin;
import com.example.apiprojeteasyhealth.dto.ConsultationInformationsForPatient;
import com.example.apiprojeteasyhealth.entity.Consultation;
import com.example.apiprojeteasyhealth.entity.Medecin;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public interface MedecinRepository extends JpaRepository<Medecin, Long> {
    Optional<Medecin> findByAdresseMail(String adresse_mail);

    @Query("SELECT m.id as idMedecin, m.nom as medecinNom, m.adresseMail as adresseMail, m.numeroTelephone as numeroTelephone, m.specialite as specialite, m.cheminFichier as photoProfil, "
            + "c.id as idConsultation, c.date as date, c.prix as prix, pa.libelle as pathologieLibelle, p.nom as patientNom, p.prenom as patientPrenom, p.adresseMail as patientMail, "
            + " p.numeroTelephone as patientTel, s.description as suiviDescription, s.etat as suiviEtat, o.dateOrdo as ordonnanceDate, o.contenu as ordonnanceContenu, "
            + "medicament.nom as medicamentNom, medicament.description as medicamentDescription, pr.quantite as prescriptionQuantite "
            + "FROM Medecin m "
            + "LEFT JOIN m.consultations c "
            + "LEFT JOIN c.pathologie pa "
            + "LEFT JOIN c.patient p "
            + "LEFT JOIN c.suivis s "
            + "LEFT JOIN c.ordonnances o "
            + "LEFT JOIN o.prescriptions pr "
            + "LEFT JOIN pr.medicament medicament")
    List<Tuple> findAllMedecinInfo();


    public default List<AllAboutMedecin> getAllMedecinsInfo() {
        List<Tuple> rawData = findAllMedecinInfo();
        Map<Long, AllAboutMedecin> resultMap = new LinkedHashMap<>();
        Map<Long, Long> consultationIdMap = new HashMap<>();

        for (Tuple row : rawData) {
            Long idMedecin = row.get("idMedecin", Long.class);
            AllAboutMedecin allAboutMedecin = resultMap.computeIfAbsent(idMedecin, k -> {
                Long genericConsultationId = 1L; // Change the type to Long and initialize with 1L
                return new AllAboutMedecin(
                        row.get("idMedecin", Long.class),
                        row.get("medecinNom", String.class),
                        row.get("adresseMail", String.class),
                        row.get("numeroTelephone", String.class),
                        row.get("specialite", String.class),
                        row.get("photoProfil", String.class),
                        new ArrayList<>()
                );
            });

            Long idConsultation = row.get("idConsultation", Long.class);
            if (idConsultation != null) {
                consultationIdMap.putIfAbsent(idConsultation, (long) (allAboutMedecin.getConsultations().size() + 1));
                Long idConsultationInfo = consultationIdMap.get(idConsultation);

                ConsultationInformationsForMedecin consultationInfo = new ConsultationInformationsForMedecin(
                        idConsultationInfo,
                        row.get("patientNom", String.class),
                        row.get("patientPrenom", String.class),
                        row.get("patientMail", String.class),
                        row.get("patientTel", String.class),
                        row.get("date", LocalDate.class),
                        row.get("prix", Float.class),
                        row.get("pathologieLibelle", String.class)
                );

                if (!allAboutMedecin.getConsultations().contains(consultationInfo)) {
                    allAboutMedecin.getConsultations().add(consultationInfo);
                }
            }
        }

        return new ArrayList<>(resultMap.values());
    }




    //Afficher les détails d'un patient :
    @Query("SELECT m.id as idMedecin, m.nom as medecinNom, m.adresseMail as adresseMail, m.numeroTelephone as numeroTelephone, m.specialite as specialite, m.cheminFichier as photoProfil "
            + "FROM Medecin m "
            + "WHERE m.adresseMail = :mail")
    Tuple findMedecinInfoByEmail(@Param("mail") String adresseMail);

    @Query("SELECT c.id as idConsultation, c.date as date, c.prix as prix, pa.libelle as pathologieLibelle, p.nom as patientNom, p.prenom as patientPrenom, p.adresseMail as patientMail, "
            + " p.numeroTelephone as patientTel "
            + "FROM Consultation c "
            + "LEFT JOIN c.pathologie pa "
            + "LEFT JOIN c.patient p "
            + "LEFT JOIN c.medecin m "
            + "WHERE m.adresseMail = :mail")
    List<Tuple> findConsultationsForMedecin(@Param("mail") String adresseMail);


    public default List<AllAboutMedecin> getMedecinInfoByEmail(String adresseMail) {
        Tuple medecinTuple = findMedecinInfoByEmail(adresseMail);
        if (medecinTuple == null) {
            return Collections.emptyList();
        }
        List<Tuple> consultationTuples = findConsultationsForMedecin(adresseMail);
        AllAboutMedecin allAboutMedecin = new AllAboutMedecin(
                medecinTuple.get("idMedecin", Long.class),
                medecinTuple.get("medecinNom", String.class),
                medecinTuple.get("adresseMail", String.class),
                medecinTuple.get("numeroTelephone", String.class),
                medecinTuple.get("specialite", String.class),
                medecinTuple.get("photoProfil", String.class),
                new ArrayList<>()
        );

        long genericConsultationId = 1;
        for (Tuple row : consultationTuples) {
            ConsultationInformationsForMedecin consuultation = new ConsultationInformationsForMedecin(
                    genericConsultationId++,
                    row.get("patientNom", String.class),
                    row.get("patientPrenom", String.class),
                    row.get("patientMail", String.class),
                    row.get("patientTel", String.class),
                    row.get("date", LocalDate.class),
                    row.get("prix", Float.class),
                    row.get("pathologieLibelle", String.class)
            );
            allAboutMedecin.getConsultations().add(consuultation);
        }

        return Collections.singletonList(allAboutMedecin);
    }

}
