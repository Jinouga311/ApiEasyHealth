package com.example.apiprojeteasyhealth.controller;

import com.example.apiprojeteasyhealth.dto.MesureDto;
import com.example.apiprojeteasyhealth.dto.MesureForPatient;
import com.example.apiprojeteasyhealth.dto.MesureForPatientAndPathologie;
import com.example.apiprojeteasyhealth.entity.Mesure;
import com.example.apiprojeteasyhealth.service.MesureService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("mesure")
public class MesureController {

    @Autowired
    private MesureService mesureService;

    @GetMapping("/mesuresPatientAvecPathalogie/{mailPatient}/{pathologie}/{dateDebut}/{dateFin}")
    @Operation(summary = "Mesures d'un patient pour un intervalle de date avec pathologie renseignée", description = "Affiche les mesures d'un patient pour une pathologie et un intervalle de dates données")
    public List<MesureForPatientAndPathologie> getMesureFromPatientAndPathologie(@PathVariable String mailPatient, @PathVariable String pathologie, @PathVariable LocalDate dateDebut, @PathVariable LocalDate dateFin){
        return mesureService.getMesureFromPatientAndPathologie(mailPatient, pathologie, dateDebut, dateFin);
    }

    @GetMapping("/mesurePatient/{mailPatient}/{dateDebut}/{dateFin}")
    @Operation(summary = "Mesures d'un patient pour un intervalle de date", description = "Affiche les mesures d'un patient pour  un intervalle de dates données")
    public List<MesureForPatient> getMesureFromPatient(@PathVariable String mailPatient, @PathVariable LocalDate dateDebut, @PathVariable LocalDate dateFin ){
        return mesureService.getMesureFromPatient(mailPatient, dateDebut, dateFin);
    }

    @PostMapping("/{descriptionSuivi}")
    public ResponseEntity<Mesure> addMesure(@RequestBody MesureDto mesureDto, @PathVariable String descriptionSuivi, @RequestParam String mailPatient) {
        Mesure mesure = mesureService.addMesure(mesureDto, descriptionSuivi, mailPatient);
        if (mesure == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(mesure);
    }
}
