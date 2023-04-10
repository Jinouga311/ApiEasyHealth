package com.example.apiprojeteasyhealth.dto;

public class FichierDto {
    private String nomFichier;
    private String fileData;

    // Constructeurs, getters et setters
    public FichierDto() {}

    public FichierDto(String nomFichier, String fileData) {
        this.nomFichier = nomFichier;
        this.fileData = fileData;
    }

    public String getNomFichier() {
        return nomFichier;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public String getFileData() {
        return fileData;
    }

    public void setFileData(String fileData) {
        this.fileData = fileData;
    }
}
