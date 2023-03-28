package com.example.apiprojeteasyhealth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_patient")
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(name= "adresse_mail", nullable = false, unique = true)
    private String adresseMail;

    @Column(name = "numero_telephone", nullable = false)
    private String numeroTelephone;

    @Column(nullable = false, unique = true)
    private String pseudo;

    @Column(name = "mot_de_passe", nullable = false)
    private String motDePasse;

    @Column(name = "chemin_photo", nullable = false)
    private String cheminFichier;

    @OneToMany(mappedBy = "patient")
    private List<Consultation> consultations;

    @OneToMany(mappedBy = "patient")
    private List<RDV> rdvs;

    @OneToMany(mappedBy = "patient")
    private List<Mesure> mesures;
}
