package com.tubolsillo.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Formato de la solicitud de refresco del token de acceso
 *
 * @param refreshToken El token de refresco
 */
public record RefreshRequest(
        @NotBlank String refreshToken
) {
}
