package com.example.apiprojeteasyhealth.repository;

import com.example.apiprojeteasyhealth.entity.Mesure;
import com.example.apiprojeteasyhealth.entity.Pathologie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PathologieRepository  extends JpaRepository<Pathologie, Long> {

    public Pathologie findByLibelle(String libelle);
}
