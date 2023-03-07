package com.example.apiprojeteasyhealth.controller;

import com.example.apiprojeteasyhealth.dto.*;
import com.example.apiprojeteasyhealth.entity.RDV;
import com.example.apiprojeteasyhealth.service.RDVService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.time.LocalTime;
import java.util.List;
import java.lang.String;

@RestController
@RequestMapping("/rdv")
public class RDVController {

    @Autowired
    private RDVService rdvService;

    @GetMapping("patient/{patientMail}")
    @Operation(summary = "RDV d'un patient", description = "Affiche la liste des rdv d'un patient")
    public List<RDVForPatient> getRDVByPatientMail(@PathVariable String patientMail){
        return rdvService.getRDVByPatientMail(patientMail);
    }

    @GetMapping("medecin/{medecinMail}")
    @Operation(summary = "RDV d'un medecin", description = "Affiche la liste des rdv d'un medecin")
    public List<RDVForMedecin> getRDVByMedecinMail(@PathVariable String medecinMail){
        return rdvService.getRDVByMedecinMail(medecinMail);
    }

    @PostMapping("/{role}/{mail}")
    @Operation(summary = "Permet à un patient, ou bien un médecin de poser un RDV", description = "Permet à un patient, ou bien un médecin de poser un RDV avec un autre utilisateur caractérisé par son mail. Un patient pourra poser un RDV avec un médecin, et un médecin avec un patient")
    public ResponseEntity<RDV> createRDV(@RequestBody RDVRequest rdvRequest, @PathVariable Role role, @PathVariable String mail) {
        RDV rdv = rdvService.createRDV(rdvRequest.getDateRDV(), rdvRequest.getHeureRDV(), rdvRequest.getHeure(),
                rdvRequest.getMailDestinataireRDV(), role, mail);
        return ResponseEntity.ok(rdv);
    }



}
