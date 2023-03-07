package com.example.apiprojeteasyhealth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientAlerte {
    private String nom;
    private String prenom;
    private String adresseMail;
    private String numeroTelephone;
    private LocalDate derniereConsultation;
}
