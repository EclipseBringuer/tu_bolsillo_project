package com.tubolsillo.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDate;

/**
 * DTO con la información para crear una transacción
 *
 * @param amount          La cantidad de la transacción
 * @param description     La descripción de la transacción
 * @param transactionDate La fecha de la transacción
 * @param categoryId      El identificador de la categoría de la transacción
 */
public record CreateTransactionDTO(
        @NotNull(message = "El monto es obligatorio")
        @DecimalMin(value = "0.01", message = "El monto debe ser mayor a cero")
        Double amount,
        String description,
        @NotNull(message = "La fecha de la transacción debe ser obligatoria")
        @PastOrPresent(message = "La fecha no puede ser futura")
        LocalDate transactionDate,
        @NotNull(message = "El ID de la categoría es obligatorio")
        Long categoryId
) {
}
