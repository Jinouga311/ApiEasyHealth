package com.example.apiprojeteasyhealth.controller;


import com.example.apiprojeteasyhealth.dto.SuiviDto;
import com.example.apiprojeteasyhealth.entity.Suivi;
import com.example.apiprojeteasyhealth.service.SuiviService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/suivis")
public class SuiviController {

    @Autowired
    private SuiviService suiviService;

    @PostMapping("/{id}")
    public ResponseEntity<Suivi> addSuivi(@RequestBody SuiviDto suiviDto, @PathVariable Long id) {
        Suivi suivi = suiviService.addSuivi(suiviDto, id);
        if (suivi == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(suivi);
    }

}
