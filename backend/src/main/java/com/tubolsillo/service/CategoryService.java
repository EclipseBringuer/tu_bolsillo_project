package com.tubolsillo.service;

import com.tubolsillo.dto.CategoryDTO;
import com.tubolsillo.dto.CreateCategoryDTO;
import com.tubolsillo.entity.Category;
import com.tubolsillo.exception.custom.CategoryRepeatedException;
import com.tubolsillo.exception.custom.ResourceNotFoundException;
import com.tubolsillo.mapper.CategoryMapper;
import com.tubolsillo.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
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
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

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

    /**
     * Devuelve la Categoría especificada por su ID
     *
     * @param id El identificador de la categoría
     * @return La categoría
     */
    public Category findCategoryEntityById(Long id) {
        // Se obtiene al usuario que realiza la petición
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        var user = userService.findByEmail(email);

        // Se obtiene la categoría
        var category = categoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("La categoría con ID=" + id + " no existe"));

        // Se comprueba que la categoría pertenezca al usuario
        if (!category.getUser().getId().equals(user.getId())) {
            log.error("La categoría con ID={} no pertenece al usuario '{}'", category.getId(), email);
            throw new ResourceNotFoundException("La categoría con ID=" + id + " no existe");
        }

        // Se devuelve su información
        return category;
    }

    /**
     * Devuelve la Categoría en formato DTO por su ID
     *
     * @param id El identificador de la categoría
     * @return La categoría en formato DTO
     */
    public CategoryDTO getCategoryDTOById(Long id) {
        return categoryMapper.toDTO(findCategoryEntityById(id));
    }

    /**
     * Elimina una categoría mediante su identificador
     *
     * @param id El identificador de la categoría
     */
    public void deleteCategory(Long id) {
        log.info("Borrando la categoría con ID={}", id);
        categoryRepository.deleteById(findCategoryEntityById(id).getId());
        log.info("La categoría con ID={} ha sido eliminada correctamente", id);
    }
}
