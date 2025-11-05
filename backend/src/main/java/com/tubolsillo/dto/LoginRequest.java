package com.tubolsillo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO con la información del intento de inicio de sesión de un usuario
 *
 * @param email    El email del usuario
 * @param password La contraseña del usuario
 */
public record LoginRequest(
        @Email
        @NotBlank
        String email,
        @NotBlank
        String password) {
}
