package com.example.apiprojeteasyhealth.controller;


import com.example.apiprojeteasyhealth.dto.SuiviDto;
import com.example.apiprojeteasyhealth.entity.Suivi;
import com.example.apiprojeteasyhealth.service.SuiviService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/suivis")
public class SuiviController {

    @Autowired
    private SuiviService suiviService;

    @PostMapping("/{idConsultation}")
    @Operation(summary = "Permet de rentrer ou mettre à jour un suivi pour un patient", description = "Cette requête rentrera un nouveau, suivi ou pour mettre à jour son état si le suivi existe, le patient suivi est identifié depuis l'id de consultation présent dans l'URL")
    public ResponseEntity<Suivi> addSuivi(@RequestBody SuiviDto suiviDto, @PathVariable Long idConsultation) {
        Suivi suivi = suiviService.addSuivi(suiviDto, idConsultation);
        if (suivi == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(suivi);
    }

}
