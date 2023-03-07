package com.example.apiprojeteasyhealth.repository;

import com.example.apiprojeteasyhealth.entity.Medicament;
import com.example.apiprojeteasyhealth.entity.Mesure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicamentRepository extends JpaRepository<Medicament, Long> {

    Medicament findByNom(String nom);

}
