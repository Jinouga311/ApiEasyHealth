package com.example.apiprojeteasyhealth.controller;

import com.example.apiprojeteasyhealth.dto.*;
import com.example.apiprojeteasyhealth.entity.RDV;
import com.example.apiprojeteasyhealth.service.RDVService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
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

    @GetMapping("id/{dateRdv}/{heureRdv}/{mailDestinataireRdv}")
    @Operation(summary = "Récupération d'un id de RDV", description = "Retourne l'id d'un RDV en fonctions des informations renseignées")
    public Long getIdRDV(@PathVariable LocalDate dateRdv, @PathVariable LocalTime heureRdv,
                         @PathVariable String mailDestinataireRdv ){
        Long rdvId = rdvService.getidRdv(dateRdv, heureRdv, mailDestinataireRdv);
        if (rdvId == null || rdvId == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Le RDV renseigné n'existe pas");
        }
        return rdvId;
    }

    @PostMapping("/{role}/{mail}")
    @Operation(summary = "Permet à un patient, ou bien un médecin de poser un RDV", description = "Permet à un patient, ou bien un médecin de poser un RDV avec un autre utilisateur caractérisé par son mail. Un patient pourra poser un RDV avec un médecin, et un médecin avec un patient")
    public ResponseEntity<RDV> createRDV(@RequestBody RDVRequest rdvRequest, @PathVariable Role role, @PathVariable String mail) {
        RDV rdv = rdvService.createRDV(rdvRequest.getDateRDV(), rdvRequest.getHeureRDV(), rdvRequest.getDuree(),
                rdvRequest.getMailDestinataireRDV(), role, mail);
        return ResponseEntity.ok(rdv);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modification d'un RDV", description = "Permet de modifier la date et/ou l'heure d'un RDV en fonction de son ID")
    public void modifierRDV(@PathVariable Long id, @RequestParam(required = false) LocalDate dateRDV, @RequestParam(required = false) LocalTime heureRDV) {
        rdvService.modifierRDV(id, dateRDV, heureRDV);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Annulation d'un RDV", description = "Permet de supprimer un RDV pour l'annuler")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRDVById(@PathVariable Long id) {
        rdvService.deleteRDVById(id);
    }





}
