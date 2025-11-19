package com.tubolsillo.controller;

import com.tubolsillo.constants.ApiRoutes;
import com.tubolsillo.dto.AuthResponse;
import com.tubolsillo.dto.LoginRequest;
import com.tubolsillo.dto.RefreshRequest;
import com.tubolsillo.dto.RegisterRequest;
import com.tubolsillo.security.jwt.JwtUtils;
import com.tubolsillo.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
     * Utilidad de JWT
     */
    private final JwtUtils jwtUtils;

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
    public ResponseEntity<AuthResponse> refresh(
            @Valid @RequestBody RefreshRequest request,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        String oldAccessToken = jwtUtils.extractTokenOrThrow(authorizationHeader);
        return ResponseEntity.ok(authService.refresh(request, oldAccessToken));
    }

    /**
     * Cierra la sesión del usuario
     */
    @PostMapping(ApiRoutes.Auth.LOGOUT)
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authorizationHeader) {
        String accessTokenToInvalidate = jwtUtils.extractTokenOrThrow(authorizationHeader);
        authService.logout(accessTokenToInvalidate);
        return ResponseEntity.ok().build();
    }
}
