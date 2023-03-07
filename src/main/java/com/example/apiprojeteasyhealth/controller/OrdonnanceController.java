package com.example.apiprojeteasyhealth.controller;

import com.example.apiprojeteasyhealth.dto.OrdonnanceDto;
import com.example.apiprojeteasyhealth.entity.Ordonnance;
import com.example.apiprojeteasyhealth.service.OrdonnanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("ordonnance")
public class OrdonnanceController {

    @Autowired
    OrdonnanceService ordonnanceService;

    @PostMapping("/{id}")
    public ResponseEntity<Ordonnance> addOrdonnance(@RequestBody OrdonnanceDto ordonnanceDto, @PathVariable Long id) {
        Ordonnance ordonnance = ordonnanceService.addOrdonnance(ordonnanceDto, id);
        if (ordonnance == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(ordonnance);
    }

}
