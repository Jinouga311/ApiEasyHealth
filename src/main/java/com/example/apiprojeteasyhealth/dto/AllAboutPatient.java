package com.example.apiprojeteasyhealth.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllAboutPatient {

    private String nom;
    private String prenom;
    private String adresseMail;
    private String numeroTelephone;
    private LocalDate dateConsultation;
    private Float prixConsultation;
    private String libellePathologie;
    private String nomMedecin;
    private String descriptionSuivi;
    private String etatSuivi;
    private String adresseMailMedecin;
    private String numeroTelephoneMedecin;
    private LocalDate dateOrdonnance;
    private String contenuOrdonnance;
    private String nomMedicament;
    private String descriptionMedicament;
    private Integer quantiteMedicament;
}
