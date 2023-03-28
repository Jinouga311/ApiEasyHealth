package com.example.apiprojeteasyhealth.controller;

import com.example.apiprojeteasyhealth.dto.AllAboutPatient;
import com.example.apiprojeteasyhealth.dto.MesureDto;
import com.example.apiprojeteasyhealth.dto.MesureForPatient;
import com.example.apiprojeteasyhealth.dto.MesureForPatientAndPathologie;
import com.example.apiprojeteasyhealth.entity.Mesure;
import com.example.apiprojeteasyhealth.service.MesureService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Base64;
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
    public List<MesureForPatient> getMesureFromPatient(@PathVariable String mailPatient, @PathVariable LocalDate dateDebut, @PathVariable LocalDate dateFin) {
        List<MesureForPatient> mesures = mesureService.getMesureFromPatient(mailPatient, dateDebut, dateFin);
        System.out.println(mesures);



        return mesures;
    }


    @PostMapping("/{descriptionSuivi}")
    @Operation(summary = "Permet de renseigner ou modifier une mesure dans le cadre d'un suivi", description = "Si une mesure n'existe pas pour un patient et un suivi donné, alors elle est crée, sinon on modifie le suivi existant")
    public ResponseEntity<Mesure> addMesure(@RequestBody MesureDto mesureDto, @PathVariable String descriptionSuivi, @RequestParam String mailPatient) {
        Mesure mesure = mesureService.addMesure(mesureDto, descriptionSuivi, mailPatient);
        if (mesure == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(mesure);
    }
}
