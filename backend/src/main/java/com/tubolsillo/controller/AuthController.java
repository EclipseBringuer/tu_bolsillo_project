package com.tubolsillo.controller;

import com.tubolsillo.constants.ApiRoutes;
import com.tubolsillo.dto.AuthResponse;
import com.tubolsillo.dto.LoginRequest;
import com.tubolsillo.dto.RefreshRequest;
import com.tubolsillo.dto.RegisterRequest;
import com.tubolsillo.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador que se encarga de la autorización de usuarios
 */
@RestController
@RequestMapping(ApiRoutes.Auth.BASE)
@RequiredArgsConstructor
public class AuthController {

    /**
     * Servicio de autenticación
     */
    private final AuthService authService;

    /**
     * Inicia la sesión del usuario devolviéndole el token
     */
    @PostMapping(ApiRoutes.Auth.LOGIN)
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    /**
     * Registra un nuevo usuario en el sistema
     */
    @PostMapping(ApiRoutes.Auth.REGISTER)
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    /**
     * Refresca el token de acceso del usuario
     */
    @PostMapping(ApiRoutes.Auth.REFRESH)
    public ResponseEntity<AuthResponse> refresh(@Valid @RequestBody RefreshRequest request) {
        return ResponseEntity.ok(authService.refresh(request));
    }

    /**
     * Cierra la sesión del usuario
     */
    @PostMapping(ApiRoutes.Auth.LOGOUT)
    public ResponseEntity<Void> logout() {
        authService.logout();
        return ResponseEntity.ok().build();
    }
}
