package com.example.apiprojeteasyhealth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RDVForPatient {
    private LocalDate dateRdv;
    private LocalTime heureRdv;
    private String nomMedecin;
    private String adresseMailMedecin;
    private String numeroTelephoneMedecin;
}
