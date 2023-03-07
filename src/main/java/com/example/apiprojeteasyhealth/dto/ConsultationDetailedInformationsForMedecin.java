package com.example.apiprojeteasyhealth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationDetailedInformationsForMedecin {
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
}
