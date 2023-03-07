package com.example.apiprojeteasyhealth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationInformationsForMedecin {
    private String nom;
    private String prenom;
    private String adresseMail;
    private String numeroTelephone;
    private LocalDate date;
    private float prix;
    private String libelle;
}