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
public class Ordonnance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ordo")
    private Long id;

    @Column(name = "date_ordo")
    private LocalDate dateOrdo;

    private String contenu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_consult")
    private Consultation consultation;

    @OneToMany(mappedBy = "ordonnance", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Prescription> prescriptions;

}