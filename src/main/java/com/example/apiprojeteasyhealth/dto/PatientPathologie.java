package com.example.apiprojeteasyhealth.dto;

import com.example.apiprojeteasyhealth.entity.Pathologie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PatientPathologie {

    private static int lastId = 0;

    private Integer id;
    private String libelle;

    public PatientPathologie(String libelle) {
        this.id = ++lastId;
        this.libelle = libelle;
    }

    public static void resetId() {
        lastId = 0;
    }
}
