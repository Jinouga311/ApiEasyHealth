package com.example.apiprojeteasyhealth.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllAboutPatient {

    private Long idPatientInfo;
    private String nom;
    private String prenom;
    private String adresseMail;
    private String numeroTelephone;

    private String photoProfil;

    private List<ConsultationInformationsForPatient> consultations;


}
