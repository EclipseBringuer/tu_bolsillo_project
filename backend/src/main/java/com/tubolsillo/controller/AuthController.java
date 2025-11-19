package com.tubolsillo.controller;

import com.tubolsillo.constants.ApiRoutes;
import com.tubolsillo.dto.AuthResponse;
import com.tubolsillo.dto.LoginRequest;
import com.tubolsillo.dto.RefreshRequest;
import com.tubolsillo.dto.RegisterRequest;
import com.tubolsillo.security.jwt.JwtUtils;
import com.tubolsillo.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador que se encarga de la autorización de usuarios
 */
@RestController
@RequestMapping(ApiRoutes.Auth.BASE)
@Tag(name = "Autenticación", description = "Operaciones para gestionar la autenticación del usuario.")
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
    @Operation(
            summary = "Iniciar Sesión",
            description = "Permite al usuario iniciar sesión en el sistema obteniendo sus tokens de acceso y refresco"
    )
    @PostMapping(ApiRoutes.Auth.LOGIN)
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    /**
     * Registra un nuevo usuario en el sistema
     */
    @Operation(
            summary = "Registrar",
            description = "Registrar a un nuevo usuario en el sistema"
    )
    @PostMapping(ApiRoutes.Auth.REGISTER)
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    /**
     * Refresca el token de acceso del usuario
     */
    @Operation(
            summary = "Refrescar",
            description = "Permite al usuario refrescar su token de acceso y de refresco"
    )
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
    @Operation(
            summary = "Cerrar Sesión",
            description = "Cierra la sesión del usuario que realiza la petición eliminando sus tokens de acceso y refresco"
    )
    @PostMapping(ApiRoutes.Auth.LOGOUT)
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authorizationHeader) {
        String accessTokenToInvalidate = jwtUtils.extractTokenOrThrow(authorizationHeader);
        authService.logout(accessTokenToInvalidate);
        return ResponseEntity.ok().build();
    }
}
