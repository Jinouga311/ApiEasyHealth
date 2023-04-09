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


    //Afficher les détails d'un patient :
    @Query("SELECT m.id as idMedecin, m.nom as medecinNom, m.adresseMail as adresseMail, m.numeroTelephone as numeroTelephone, m.specialite as specialite, m.cheminFichier as photoProfil, "
            + "c.id as idConsultation, c.date as date, c.prix as prix, pa.libelle as pathologieLibelle, p.nom as patientNom, p.prenom as patientPrenom, p.adresseMail as patientMail, "
            + " p.numeroTelephone as patientTel, s.description as suiviDescription, s.etat as suiviEtat, o.dateOrdo as ordonnanceDate, o.contenu as ordonnanceContenu, "
            + "medicament.nom as medicamentNom, medicament.description as medicamentDescription, pr.quantite as prescriptionQuantite "
            + "FROM Patient p "
            + "LEFT JOIN p.consultations c "
            + "LEFT JOIN c.pathologie pa "
            + "LEFT JOIN c.medecin m "
            + "LEFT JOIN c.suivis s "
            + "LEFT JOIN c.ordonnances o "
            + "LEFT JOIN o.prescriptions pr "
            + "LEFT JOIN pr.medicament medicament "
            + "WHERE m.adresseMail = :mail")
    List<Tuple> findMedecinInfoByEmail(@Param("mail") String adresseMail);


    public default List<AllAboutMedecin> getMedecinInfoByEmail(String adresseMail) {
        List<Tuple> rawData = findMedecinInfoByEmail(adresseMail);
        Map<Long, AllAboutMedecin> resultMap = new LinkedHashMap<>();
        Map<Long, Long> consultationIdMap = new HashMap<>();
        long genericConsultationId = 1;

        for (Tuple row : rawData) {
            Long idMedecin = row.get("idMedecin", Long.class);
            AllAboutMedecin allAboutMedecin = resultMap.computeIfAbsent(idMedecin, k -> {
                long idMedecinInfo = resultMap.size() + 1;
                return new AllAboutMedecin(
                        idMedecinInfo,
                        row.get("medecinNom", String.class),
                        row.get("adresseMail", String.class),
                        row.get("numeroTelephone", String.class),
                        row.get("specialite", String.class),
                        row.get("photoProfil", String.class),
                        new ArrayList<>()
                );
            });

            Long idConsultation = row.get("idConsultation", Long.class);
            if(idConsultation != null && !consultationIdMap.containsKey(idConsultation)){
                consultationIdMap.put(idConsultation, genericConsultationId);
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

        }

        return new ArrayList<>(resultMap.values());
    }
}
