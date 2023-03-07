package com.example.apiprojeteasyhealth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RDVForMedecin {
    private LocalDate dateRdv;
    private LocalTime heureRdv;
    private String nomPatient;
    private String adresseMailPatient;
    private String numeroTelephonePatient;

}
