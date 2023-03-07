package com.example.apiprojeteasyhealth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistor {
    private String nom;
    private String prenom;
    private String adresseMail;
    private String numeroTelephone;
    private String pseudo;
    private String motDePasse;
}