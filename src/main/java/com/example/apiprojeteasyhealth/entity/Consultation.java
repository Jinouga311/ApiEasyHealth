package com.example.apiprojeteasyhealth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consult")
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    private Float prix;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pathologie")
    private Pathologie pathologie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_patient")
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_medecin")
    private Medecin medecin;

    @OneToMany(mappedBy = "consultation")
    private List<Suivi> suivis;

    @OneToMany(mappedBy = "consultation")
    private List<Ordonnance> ordonnances;
}
