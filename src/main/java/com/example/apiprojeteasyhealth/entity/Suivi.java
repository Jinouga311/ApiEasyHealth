package com.example.apiprojeteasyhealth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Suivi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_suivi")
    private Long id;

    @Column(nullable = false)
    private String description;

    private String etat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_consult")
    private Consultation consultation;

    @OneToMany(mappedBy = "suivi", cascade = CascadeType.ALL)
    private List<Mesure> mesures;
}
