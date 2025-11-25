package com.tubolsillo.controller;

import com.tubolsillo.constants.ApiRoutes;
import com.tubolsillo.dto.UserDTO;
import com.tubolsillo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador encargado de recibir las peticiones relacionadas con la entidad usuario
 */
@RestController
@RequestMapping(ApiRoutes.User.BASE)
@Tag(name = "Usuarios", description = "Operaciones para gestionar y buscar usuarios.")
@RequiredArgsConstructor
public class UserController {

    /**
     * Servicio de la entidad usuario
     */
    private final UserService userService;

    /**
     * Obtiene la información del usuario actual
     */
    @Operation(
            summary = "Obtener usuario actual",
            description = "Obtiene la información del usuario que realiza la petición"
    )
    @GetMapping(ApiRoutes.User.ME)
    public ResponseEntity<UserDTO> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    /**
     * Obtiene un usuario activo por su ID
     */
    @Operation(
            summary = "Obtener usuario por ID",
            description = "Obtiene la información de un usuario activo por su identificador"
    )
    @GetMapping(ApiRoutes.BY_ID)
    public ResponseEntity<UserDTO> getUserById(
            @Parameter(description = "Identificador único del usuario (Long)", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserDTOById(id));
    }

    /**
     * Elimina lógicamente el usuario indicado
     */
    @Operation(
            summary = "Borrar usuario",
            description = "Borra a un usuario de forma lógica en el sistema"
    )
    @DeleteMapping(ApiRoutes.BY_ID)
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "Identificador único del usuario (Long)", required = true)
            @PathVariable Long id) {
        userService.softDeleteUser(id); // Borrado suave
        return ResponseEntity.noContent().build();
    }

    /**
     * Restaura un usuario eliminado lógicamente
     */
    @Operation(
            summary = "Restaurar usuario",
            description = "Restaura a un usuario que esté eliminado de forma lógica"
    )
    @PatchMapping(ApiRoutes.User.RESTORE)
    public ResponseEntity<Void> restoreUser(
            @Parameter(description = "Identificador único del usuario (Long)", required = true)
            @PathVariable Long id) {
        userService.restoreUser(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Verifica si existe un usuario por email
     */
    @Operation(
            summary = "Comprobar usuario",
            description = "Comprueba si un usuario existe en el sistema mediante su email"
    )
    @GetMapping(ApiRoutes.EXISTS)
    public ResponseEntity<Boolean> userExists(
            @Parameter(description = "Email del usuario (String)", required = true)
            @RequestParam String email) {
        return ResponseEntity.ok(userService.existsByEmail(email));
    }
}
