package com.example.apiprojeteasyhealth.controller;

import com.example.apiprojeteasyhealth.dto.ApiResponse;
import com.example.apiprojeteasyhealth.dto.Role;
import com.example.apiprojeteasyhealth.dto.UserLogin;
import com.example.apiprojeteasyhealth.dto.UserRegistor;
import com.example.apiprojeteasyhealth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;





    @PostMapping(value= "/register/{role}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_OCTET_STREAM_VALUE})
    @Operation(summary = "Permet à un médecin ou un patient de s'inscrire", description = "En fonction du rôle choisit, un médecin ou un patient peuvent s'inscrire, pour rappel un patient doit s'inscrire depuis l'appli mobile, l'admin web peut quant à lui inscrire un médecin et un patient si il le souhaite depuis une appli web")
    public ResponseEntity<?> registerUser(
            @RequestPart(value = "file", required = true) @Parameter(description = "Fichier à envoyer") MultipartFile file,
            @RequestPart(value = "userRegistor", required = true) @Parameter(description = "Informations de l'utilisateur", schema = @Schema(type = "object", implementation = UserRegistor.class)) UserRegistor userRegistor,
            @PathVariable Role role) throws IOException {
        authService.registerUser(file, userRegistor, role);
        return ResponseEntity.ok(new ApiResponse(true, "Utilisateur enregistré avec succès"));
    }





    @PostMapping("/login/{role}")
    @Operation(summary = "Permet à un médecin ou un patient de se connecter", description = "En fonction du rôle choisit, un médecin ou un patient peuvent se connecter si ils sont bien inscris, un patient se connectera depuis une appli mobile, un médecin depuis une appli web")
    public ResponseEntity<?> loginUser(@RequestBody UserLogin userLogin, @PathVariable Role role) {
        String role1 = role.toString();
        String adresseMail = userLogin.getAdresseMail();
        String motDePasse = userLogin.getMotDePasse();

        // Récupération de l'utilisateur correspondant à l'adresse mail et au rôle donné
        Optional<?> user = null;
        if (role1.equals(Role.PATIENT.toString())) {
            user = authService.loginPatient(adresseMail, motDePasse);
        } else if (role1.equals(Role.MEDECIN.toString())) {
            user = authService.loginMedecin(adresseMail, motDePasse);
        }

        // Si l'utilisateur n'a pas été trouvé, retourner une erreur 401 Unauthorized
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // Sinon, retourner une réponse 200 OK
        return ResponseEntity.ok(new ApiResponse(true, "Utilisateur connecté avec succès"));
    }
}

