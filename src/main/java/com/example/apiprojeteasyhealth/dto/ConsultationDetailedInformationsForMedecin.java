package com.example.apiprojeteasyhealth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationDetailedInformationsForMedecin {

    private Long idConsultation;
    private String medecinMail;
    private String patientNom;
    private String patientPrenom;
    private String patientMail;
    private String patientTelephone;
    private LocalDate consultationDate;
    private Float consultationPrix;
    private String pathologieLibelle;
    private String medecinNom;
    private String suiviDescription;
    private String suiviEtat;
    private LocalDate ordonnanceDate;
    private String ordonnanceContenu;
    private String medicamentNom;
    private String medicamentDescription;
    private Integer prescriptionQuantite;

    public ConsultationDetailedInformationsForMedecin(String medecinMail, String patientNom, String patientPrenom, String patientMail, String patientTelephone,
                                                      LocalDate consultationDate, Float consultationPrix, String pathologieLibelle, String medecinNom, String suiviDescription,
                                                      String suiviEtat, LocalDate ordonnanceDate, String ordonnanceContenu, String medicamentNom, String medicamentDescription, Integer prescriptionQuantite){
        this.medecinMail = medecinMail;
        this.patientNom = patientNom;
        this.patientPrenom = patientPrenom;
        this.patientMail = patientMail;
        this.patientTelephone = patientTelephone;
        this.consultationDate = consultationDate;
        this.consultationPrix = consultationPrix;
        this.pathologieLibelle = pathologieLibelle;
        this.medecinNom = medecinNom;
        this.suiviDescription = suiviDescription;
        this.suiviEtat = suiviEtat;
        this.ordonnanceDate = ordonnanceDate;
        this.ordonnanceContenu = ordonnanceContenu;
        this.medicamentNom = medicamentNom;
        this.medicamentDescription = medicamentDescription;
        this.prescriptionQuantite = prescriptionQuantite;
    }
}
