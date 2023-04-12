package com.example.apiprojeteasyhealth.controller;

import com.example.apiprojeteasyhealth.dto.ConsultationDetailedInformationsForMedecin;
import com.example.apiprojeteasyhealth.dto.ConsultationDto;
import com.example.apiprojeteasyhealth.dto.ConsultationInformationsForMedecin;
import com.example.apiprojeteasyhealth.dto.ConsultationInformationsForPatient;
import com.example.apiprojeteasyhealth.entity.Consultation;
import com.example.apiprojeteasyhealth.service.ConsultationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultations")
public class ConsultationController {

    @Autowired
    private ConsultationService consultationService;

    @GetMapping("medecin/{medecinMail}")
    @Operation(summary = "Consultations d'un médecin", description = "Afficher la liste des patients suivis par un médecin")
    public List<ConsultationInformationsForMedecin> getAllConsultationsByMedecinMail(@PathVariable String medecinMail) {
        return consultationService.getAllConsultationsByMedecinMail(medecinMail);
    }

    @GetMapping("medecin/detaille/{medecinMail}")
    @Operation(summary = "Consultations en details d'un médecin", description = "Aficher la liste des patients suivis par un médecin en détail ")
    public List<ConsultationDetailedInformationsForMedecin> getAllDeatilledConsultationsByMedecinMail(@PathVariable String medecinMail){
        return consultationService.getAllDetailledConsultationsByMedecinMail(medecinMail);
    }

    @GetMapping("/patient/{patientMail}")
    @Operation(summary = "Consultations d'un patient avec ou sans suivi", description = "Affiche entièrement la liste des consultations d'un patient")
    public List<ConsultationInformationsForPatient> getConsultationsByPatientMail(@PathVariable String patientMail) {
        return consultationService.getConsultationsByPatientMail(patientMail);
    }

    @GetMapping("patient/suivi/{patientMail}")
    @Operation(summary = "Consultations d'un patient avec suivi", description = "Affiche uniquement la liste des consultations d'un patient avec suivi ")
    public List<ConsultationInformationsForPatient> getConsultationsInformationsAvecSuiviForPatientByMail(@PathVariable String patientMail){
        return consultationService.getConsultationsInformationsAvecSuiviForPatientByMail(patientMail);
    }

    @PostMapping("medecin/saisir/{medecinMail}")
    @Operation(summary = "Le moment du rdv venu, la consultation a donc lieu", description = "Si les attributs date et heure du Json coincident avec la date et l'heure d'un rdv qui existe, alors une consultation peut être entrée. Si cette requête est un succès, elle renvoie alors l'id de la consultation pour l'utiliser sur d'autres requête")
    public ResponseEntity<Long> addConsultation(@RequestBody ConsultationDto consultationDto, @PathVariable String medecinMail) {
        Consultation consultation = consultationService.addConsultation(consultationDto, medecinMail);
        if (consultation == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(consultation.getId());
    }

}