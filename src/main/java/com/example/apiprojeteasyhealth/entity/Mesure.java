package com.example.apiprojeteasyhealth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Mesure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mesure")
    private Long id;
    private Float valeur;

    private String unite;

    private String periode;

    @Column(name= "date_mesure")
    private LocalDate dateMesure;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_suivi")
    private Suivi suivi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_patient")
    private Patient patient;

}