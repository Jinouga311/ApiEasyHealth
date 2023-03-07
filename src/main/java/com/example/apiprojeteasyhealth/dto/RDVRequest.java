package com.example.apiprojeteasyhealth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RDVRequest {
    private String dateRDV;
    private String heureRDV;
    private String heure;
    private String mailDestinataireRDV;
}
