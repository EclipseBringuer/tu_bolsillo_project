package com.tubolsillo.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO con la información de una transacción
 *
 * @param id              El identificador de la transacción
 * @param amount          La cantidad
 * @param description     Descripción
 * @param transactionDate Fecha en la que se realizó
 * @param category        La categoría
 */
public record TransactionDTO(
        Long id,
        BigDecimal amount,
        String description,
        LocalDate transactionDate,
        CategoryDTO category) {
}
