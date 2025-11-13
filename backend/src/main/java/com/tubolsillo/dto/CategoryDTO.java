package com.tubolsillo.dto;

import com.tubolsillo.entity.enums.Type;

/**
 * DTO con la información de una categoría
 *
 * @param id   Identificador
 * @param name Nombre de la categoría
 * @param type El tipo de la categoría
 */
public record CategoryDTO(Long id, String name, Type type) {
}
