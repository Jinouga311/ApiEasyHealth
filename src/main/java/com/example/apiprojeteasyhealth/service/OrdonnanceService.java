package com.example.apiprojeteasyhealth.service;

import com.example.apiprojeteasyhealth.dto.OrdonnanceDto;
import com.example.apiprojeteasyhealth.dto.PrescriptionDto;
import com.example.apiprojeteasyhealth.entity.Consultation;
import com.example.apiprojeteasyhealth.entity.Medicament;
import com.example.apiprojeteasyhealth.entity.Ordonnance;
import com.example.apiprojeteasyhealth.entity.Prescription;
import com.example.apiprojeteasyhealth.repository.ConsultationRepository;
import com.example.apiprojeteasyhealth.repository.MedicamentRepository;
import com.example.apiprojeteasyhealth.repository.OrdonnanceRepository;
import com.example.apiprojeteasyhealth.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrdonnanceService {

    @Autowired
    private OrdonnanceRepository ordonnanceRepository;

    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private MedicamentRepository medicamentRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    public Ordonnance addOrdonnance(OrdonnanceDto ordonnanceDto, Long consultationId) {
        // Vérifier l'existence de la consultation
        Optional<Consultation> consultationOptional = consultationRepository.findById(consultationId);
        if (consultationOptional.isEmpty()) {
            return null;
        }
        Consultation consultation = consultationOptional.get();

        // Vérifier l'existence des médicaments et créer ceux qui n'existent pas
        List<PrescriptionDto> prescriptionDtos = ordonnanceDto.getPrescriptions();
        List<Prescription> prescriptions = new ArrayList<>();
        for (PrescriptionDto prescriptionDto : prescriptionDtos) {
            String medicamentNom = prescriptionDto.getMedicamentNom();
            Medicament medicament = medicamentRepository.findByNom(medicamentNom);
            if (medicament == null) {
                medicament = new Medicament();
                medicament.setNom(medicamentNom);
                medicament.setDescription(prescriptionDto.getMedicamentDescription());
                medicamentRepository.save(medicament);
            }
            Prescription prescription = new Prescription();
            prescription.setMedicament(medicament);
            prescription.setQuantite(prescriptionDto.getQuantite());
            prescriptions.add(prescription);
        }

        // Créer l'ordonnance
        Ordonnance ordonnance = new Ordonnance();
        ordonnance.setDateOrdo(ordonnanceDto.getDateOrdo());
        ordonnance.setContenu(ordonnanceDto.getPrescriptions().get(0).getMedicamentNom());
        ordonnance.setConsultation(consultation);
        ordonnanceRepository.save(ordonnance);

        // Ajouter les prescriptions à l'ordonnance
        for (Prescription prescription : prescriptions) {
            prescription.setOrdonnance(ordonnance);
            prescriptionRepository.save(prescription);
        }

        return ordonnance;
    }


}