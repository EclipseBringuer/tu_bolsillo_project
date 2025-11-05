package com.tubolsillo.dto;

import java.time.LocalDateTime;

/**
 * DTO que representa un mensaje de error
 *
 * @param status    El código de estado HTTP
 * @param message   El mensaje de error
 * @param timestamp La fecha y hora del error
 */
public record ErrorResponse(int status, String message, LocalDateTime timestamp) {
}
