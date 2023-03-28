package com.example.apiprojeteasyhealth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuiviDto {

    private Integer id;
    private String description;
    private String etat;
}
