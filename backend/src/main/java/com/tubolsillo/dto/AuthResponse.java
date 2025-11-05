package com.tubolsillo.dto;

/**
 * DTO con los tokens de acceso y refresco
 *
 * @param accessToken  Token de acceso que caduca rápido
 * @param refreshToken Token de refresco del token de acceso, con larga duración
 */
public record AuthResponse(String accessToken, String refreshToken) {
}
