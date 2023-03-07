package com.example.apiprojeteasyhealth.repository;

import com.example.apiprojeteasyhealth.entity.Mesure;
import com.example.apiprojeteasyhealth.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionRepository  extends JpaRepository<Prescription, Long> {

}
