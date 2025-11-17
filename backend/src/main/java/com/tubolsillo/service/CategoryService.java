package com.tubolsillo.service;

import com.tubolsillo.dto.CategoryDTO;
import com.tubolsillo.mapper.CategoryMapper;
import com.tubolsillo.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio con las funcionalidades relacionadas con la entidad Category
 */
@Service
@AllArgsConstructor
@Slf4j
public class CategoryService {

    /**
     * Repositorio de la entidad Category
     */
    private final CategoryRepository categoryRepository;

    /**
     * Conversor de la entidad category
     */
    private final CategoryMapper categoryMapper;

    /**
     * Obtiene el listado de categorías pertenecientes al usuario actual
     *
     * @return El listado de categorías del usuario
     */
    public List<CategoryDTO> getCurrentUserCategories() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        log.info("Obteniendo las categorías del usuario '{}'", email);
        var categories = categoryRepository.findAllByUserEmail(email);
        return categories.stream().map(categoryMapper::toDTO).toList();
    }
}
