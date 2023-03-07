package com.example.apiprojeteasyhealth.service;


import com.example.apiprojeteasyhealth.dto.SuiviDto;
import com.example.apiprojeteasyhealth.entity.Consultation;
import com.example.apiprojeteasyhealth.entity.Suivi;
import com.example.apiprojeteasyhealth.repository.ConsultationRepository;
import com.example.apiprojeteasyhealth.repository.SuiviRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SuiviService {
    @Autowired
    private SuiviRepository suiviRepository;

    @Autowired
    private ConsultationRepository consultationRepository;

    public Suivi addSuivi(SuiviDto suiviDto, Long consultationId) {
        // Vérifier l'existence de la consultation
        Optional<Consultation> consultationOptional = consultationRepository.findById(consultationId);
        if (consultationOptional.isEmpty()) {
            return null;
        }
        Consultation consultation = consultationOptional.get();

        // Vérifier l'existence d'un suivi pour la consultation et la description donnée
        Suivi existingSuivi = suiviRepository.findByConsultationAndDescription(consultation, suiviDto.getDescription());
        if (existingSuivi != null) {
            // Si le suivi existe déjà, mettre à jour l'état
            existingSuivi.setEtat(suiviDto.getEtat());
            suiviRepository.save(existingSuivi);
            return existingSuivi;
        } else {
            // Sinon, créer le suivi
            Suivi suivi = new Suivi();
            suivi.setDescription(suiviDto.getDescription());
            suivi.setEtat(suiviDto.getEtat());
            suivi.setConsultation(consultation);
            suiviRepository.save(suivi);
            return suivi;
        }
    }




}
