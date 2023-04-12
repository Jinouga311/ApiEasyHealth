package com.example.apiprojeteasyhealth.dto;

import java.time.LocalDate;
import java.util.Date;

import com.example.apiprojeteasyhealth.entity.Ordonnance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultationInformationsForPatient {

    private Long idConsultation;
    private LocalDate date;
    private Float prix;
    private String pathologieLibelle;
    private String medecinNom;
    private String suiviDescription;
    private String suiviEtat;
    private OrdonnanceDto ordonnance;
}
