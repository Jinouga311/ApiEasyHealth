package com.example.apiprojeteasyhealth.controller;

import com.example.apiprojeteasyhealth.dto.OrdonnanceDto;
import com.example.apiprojeteasyhealth.entity.Ordonnance;
import com.example.apiprojeteasyhealth.service.OrdonnanceService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ordonnance")
public class OrdonnanceController {

    @Autowired
    OrdonnanceService ordonnanceService;

    @PostMapping("/{idConsultation}")
    @Operation(summary = "Lors d'une consultation, un médecin peut être amené à saisir une ordonnance médecial", description = "Cette requête permet la saisie d'une ordonnance, avec donc une préscription médical pour prescrire un médicament à un patient")
    public ResponseEntity<Ordonnance> addOrdonnance(@RequestBody OrdonnanceDto ordonnanceDto, @PathVariable Long idConsultation) {
        Ordonnance ordonnance = ordonnanceService.addOrdonnance(ordonnanceDto, idConsultation);
        if (ordonnance == null) {
            ResponseEntity.ok(null);
        }
        return ResponseEntity.ok(null);
    }

}
