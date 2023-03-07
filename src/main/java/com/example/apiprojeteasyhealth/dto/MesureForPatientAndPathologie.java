package com.example.apiprojeteasyhealth.dto;

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
    private Float valeur;
    private String unite;
    private String periode;

    private LocalDate date;
}