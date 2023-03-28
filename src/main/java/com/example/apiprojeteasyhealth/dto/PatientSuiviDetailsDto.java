package com.example.apiprojeteasyhealth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientSuiviDetailsDto {
    private Long idPatient;
    private String nom;
    private String prenom;

    private String photoProfil;
    private String adresseMail;
    private String numeroTelephone;

    private String adresseMailMedecin;
    private String nomMedecin;
    private List<SuiviDto> suivis = new ArrayList<>();
}