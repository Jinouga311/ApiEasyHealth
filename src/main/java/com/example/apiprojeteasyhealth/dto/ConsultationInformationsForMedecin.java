package com.example.apiprojeteasyhealth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationInformationsForMedecin {
    private Long idConsultation;
    private String nom;
    private String prenom;
    private String adresseMail;
    private String numeroTelephone;
    private LocalDate date;
    private float prix;
    private String libelle;

    public ConsultationInformationsForMedecin(String nom, String prenom, String adresseMail, String numeroTelephone, LocalDate date, float prix, String libelle) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresseMail = adresseMail;
        this.numeroTelephone = numeroTelephone;
        this.date = date;
        this.prix = prix;
        this.libelle = libelle;
    }

}