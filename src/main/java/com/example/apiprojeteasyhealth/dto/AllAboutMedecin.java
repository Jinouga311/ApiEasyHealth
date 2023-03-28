package com.example.apiprojeteasyhealth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllAboutMedecin {
    private Long idMedecin;
    private String nom;
    private String adresseMail;
    private String numeroTelephone;
    private String cheminFichier;

    private List<ConsultationInformationsForMedecin> consultations = new ArrayList<>();
}
