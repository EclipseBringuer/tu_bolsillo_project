package com.tubolsillo.controller;

import com.tubolsillo.constants.ApiRoutes;
import com.tubolsillo.dto.CategoryDTO;
import com.tubolsillo.dto.CreateCategoryDTO;
import com.tubolsillo.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador encargado de recibir las peticiones relacionadas con Category
 */
@RestController
@RequestMapping(ApiRoutes.Category.BASE)
@AllArgsConstructor
public class CategoryController {

    /**
     * Servicio de la entidad Category
     */
    private final CategoryService categoryService;

    /**
     * Devuelve las categorías del usuario que realiza la petición
     *
     * @return El listado con las categorías del usuario
     */
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getCurrentUserCategories() {
        return ResponseEntity.ok(categoryService.getCurrentUserCategories());
    }

    /**
     * Crea una categoría nueva para el usuario que realiza la petición
     *
     * @param categoryDTO La información de la categoría a crear
     * @return La categoría ya creada
     */
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CreateCategoryDTO categoryDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.saveCategory(categoryDTO));
    }
}
