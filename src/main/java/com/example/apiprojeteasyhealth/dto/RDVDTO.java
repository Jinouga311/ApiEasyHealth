package com.example.apiprojeteasyhealth.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RDVDTO {
    private LocalDate dateRDV;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime heureRDV;

    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime heure;

    private String mailPatient;
    private String mailMedecin;
}
