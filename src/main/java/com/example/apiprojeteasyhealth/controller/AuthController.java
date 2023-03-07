package com.example.apiprojeteasyhealth.controller;

import com.example.apiprojeteasyhealth.dto.ApiResponse;
import com.example.apiprojeteasyhealth.dto.Role;
import com.example.apiprojeteasyhealth.dto.UserLogin;
import com.example.apiprojeteasyhealth.dto.UserRegistor;
import com.example.apiprojeteasyhealth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
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



    @PostMapping("/register/{role}")
    @Operation(summary = "Permet à un médecin ou un patient de s'inscrire", description = "En fonction du rôle choisit, un médecin ou un patient peuvent s'inscrire, pour rappel un patient doit s'inscrire depuis l'appli mobile, l'admin web peut quant à lui inscrire un médecin et un patient si il le souhaite depuis une appli web")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistor userRegistor, @PathVariable Role role) {
        authService.registerUser(userRegistor, role);
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

