package com.example.apiprojeteasyhealth.controller;

import com.example.apiprojeteasyhealth.dto.ApiResponse;
import com.example.apiprojeteasyhealth.dto.Role;
import com.example.apiprojeteasyhealth.dto.UserLogin;
import com.example.apiprojeteasyhealth.dto.UserRegistor;
import com.example.apiprojeteasyhealth.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }



    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistor userRegistor) {
        authService.registerUser(userRegistor);
        return ResponseEntity.ok(new ApiResponse(true, "Utilisateur enregistré avec succès"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserLogin userLogin) {
        String role = userLogin.getRole().toString();
        String adresseMail = userLogin.getAdresseMail();
        String motDePasse = userLogin.getMotDePasse();

        // Récupération de l'utilisateur correspondant à l'adresse mail et au rôle donné
        Optional<?> user = null;
        if (role.equals(Role.PATIENT.toString())) {
            user = authService.loginPatient(adresseMail, motDePasse);
        } else if (role.equals(Role.MEDECIN.toString())) {
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

