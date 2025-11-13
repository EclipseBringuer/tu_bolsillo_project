package com.tubolsillo.mapper;

import com.tubolsillo.dto.CategoryDTO;
import com.tubolsillo.entity.Category;
import org.mapstruct.Mapper;

/**
 * Conversor de la entidad Category
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper {

    /**
     * Convierte la categoría a DTO de información
     *
     * @param category La categoría a convertir
     * @return El DTO informativo
     */
    CategoryDTO toDTO(Category category);
}
