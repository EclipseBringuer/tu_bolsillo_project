package com.tubolsillo.service;

import com.tubolsillo.dto.CategoryDTO;
import com.tubolsillo.dto.CreateCategoryDTO;
import com.tubolsillo.entity.Category;
import com.tubolsillo.exception.custom.CategoryRepeatedException;
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
     * Servicio de la entidad User
     */
    private final UserService userService;

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

    /**
     * Crea una categoría nueva para un usuario
     *
     * @param categoryDTO La información de la categoría a crear
     * @return La información de la categoría ya creada
     */
    public CategoryDTO saveCategory(CreateCategoryDTO categoryDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        if (categoryRepository.existsByUserEmailAndName(email, categoryDTO.name())) {
            log.error("La categoría '{}' ya existe para el usuario '{}'", categoryDTO.name(), email);
            throw new CategoryRepeatedException("La categoría '" + categoryDTO.name() + "' ya existe");
        }

        var user = userService.findByEmail(email);

        var newCategory = Category.builder()
                .user(user)
                .name(categoryDTO.name())
                .type(categoryDTO.type())
                .build();

        log.info("Creando la categoría '{}' para el usuario '{}'", newCategory.getName(), email);
        categoryRepository.save(newCategory);
        log.info("Categoría '{}' creada con éxito", newCategory.getName());

        return new CategoryDTO(newCategory.getId(), newCategory.getName(), newCategory.getType());
    }
}
