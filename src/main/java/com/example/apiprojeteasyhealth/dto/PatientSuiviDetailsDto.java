package com.example.apiprojeteasyhealth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientSuiviDetailsDto {
    private Long idPatient;
    private String nom;
    private String prenom;
    private String adresseMail;
    private String numeroTelephone;
    private Long idSuivi;
    private String description;
    private String etat;
    private Long idMedecin;
    private String nomMedecin;
    private String mailMedecin;
    private String telMedecin;
    private Long idConsult;
    private LocalDate date;
    private Float prix;
    private Long idPathologie;
    private String libellePathologie;
    private Long idOrdonnance;
    private LocalDate dateOrdonnance;
    private String contenuOrdonnance;
    private Long idPrescription;
    private int quantitePrescription;
    private Long idMedicament;
    private String nomMedicament;
    private String descMedicament;
    private Long idMesure;
    private Float valeurMesure;
    private String uniteMesure;
    private String periodeMesure;
}
