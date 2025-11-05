package com.tubolsillo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO con la información del intento de registro de un usuario
 *
 * @param firstName El nombre del usuario
 * @param lastName  Los apellidos del usuario
 * @param email     El email del usuario
 * @param password  La contraseña del usuario
 */
public record RegisterRequest(
        @NotBlank
        String firstName,
        @NotBlank
        String lastName,
        @NotBlank
        @Email
        String email,
        @NotBlank
        String password
) {
}
