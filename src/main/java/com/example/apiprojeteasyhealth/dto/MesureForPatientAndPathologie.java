package com.example.apiprojeteasyhealth.dto;

import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MesureForPatientAndPathologie {


    public MesureForPatientAndPathologie(Float valeur, String unite, String periode, LocalDate date, String nomMesure, String pathologie) {
        this.valeur = valeur;
        this.unite = unite;
        this.periode = periode;
        this.date = date;
        this.pathologie = pathologie;
        this.nomMesure = nomMesure;
    }

    @Transient
    private Long idMesure;

    private Float valeur;
    private String unite;
    private String periode;
    private LocalDate date;
    private String pathologie;

    private String nomMesure;
}
