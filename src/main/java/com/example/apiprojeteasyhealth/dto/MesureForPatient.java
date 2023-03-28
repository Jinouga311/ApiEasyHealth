package com.example.apiprojeteasyhealth.dto;

import jakarta.persistence.PostLoad;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MesureForPatient {

    public MesureForPatient(Float valeur, String unite, String periode, LocalDate date, String pathologie) {
        this.valeur = valeur;
        this.unite = unite;
        this.periode = periode;
        this.date = date;
        this.pathologie = pathologie;
    }

    @Transient
    private Long idMesure;

    private Float valeur;
    private String unite;
    private String periode;
    private LocalDate date;
    private String pathologie;
}