package com.example.apiprojeteasyhealth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "admin_web")
public class AdminWeb {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_admin")
    private Long id;
    private String nom;

    private String prenom;
    @Column(name = "adresse_mail")
    private String adresseMail;

    private String pseudo;
    @Column(name = "mot_de_passe")
    private String motDePasse;

}