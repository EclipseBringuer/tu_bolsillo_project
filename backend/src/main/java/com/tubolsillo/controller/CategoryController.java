package com.tubolsillo.controller;

import com.tubolsillo.constants.ApiRoutes;
import com.tubolsillo.dto.CategoryDTO;
import com.tubolsillo.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
