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

    private Long idRdv;
   private LocalDate dateRdv;
    private LocalTime heureRdv;
    private LocalTime duree;
    private String nomPatient;
    private String adresseMailPatient;
    private String numeroTelephonePatient;

    public RDVForMedecin(LocalDate dateRdv, LocalTime heureRdv, LocalTime duree, String nomPatient, String adresseMailPatient, String numeroTelephonePatient) {
        this.dateRdv = dateRdv;
        this.heureRdv = heureRdv;
        this.duree = duree;
        this.nomPatient = nomPatient;
        this.adresseMailPatient = adresseMailPatient;
        this.numeroTelephonePatient = numeroTelephonePatient;
    }




}
