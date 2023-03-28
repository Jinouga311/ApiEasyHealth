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
    private Long idRdv;
    private LocalDate dateRdv;
    private LocalTime heureRdv;
    private LocalTime duree;
    private String nomMedecin;
    private String adresseMailMedecin;
    private String numeroTelephoneMedecin;

    public RDVForPatient(LocalDate dateRdv, LocalTime heureRdv, LocalTime duree, String nomMedecin, String adresseMailMedecin, String numeroTelephoneMedecin) {
        this.dateRdv = dateRdv;
        this.heureRdv = heureRdv;
        this.duree = duree;
        this.nomMedecin = nomMedecin;
        this.adresseMailMedecin = adresseMailMedecin;
        this.numeroTelephoneMedecin = numeroTelephoneMedecin;
    }

}
