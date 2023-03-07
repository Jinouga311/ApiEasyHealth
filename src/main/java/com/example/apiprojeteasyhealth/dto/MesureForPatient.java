package com.example.apiprojeteasyhealth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MesureForPatient {
    private Float valeur;
    private String unite;
    private String periode;
    private LocalDate date;
    private String pathologie;
}