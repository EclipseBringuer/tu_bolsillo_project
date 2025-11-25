package com.tubolsillo.dto;

import com.tubolsillo.entity.enums.Type;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para crear una categoría nueva
 *
 * @param name El nombre de la categoría
 * @param type El tipo de la categoría
 */
public record CreateCategoryDTO(
        @NotBlank
        String name,
        @NotNull
        Type type) {
}
