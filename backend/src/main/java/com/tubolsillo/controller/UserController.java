package com.tubolsillo.controller;

import com.tubolsillo.constants.ApiRoutes;
import com.tubolsillo.dto.UserDTO;
import com.tubolsillo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador encargado de recibir las peticiones relacionadas con la entidad usuario
 */
@RestController
@RequestMapping(ApiRoutes.User.BASE)
@RequiredArgsConstructor
public class UserController {

    /**
     * Servicio de la entidad usuario
     */
    private final UserService userService;

    /**
     * Obtiene la información del usuario actual
     */
    @GetMapping(ApiRoutes.User.ME)
    public ResponseEntity<UserDTO> getCurrentUSer() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    /**
     * Elimina lógicamente el usuario indicado
     */
    @DeleteMapping(ApiRoutes.BY_ID)
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.softDeleteUser(id); // Borrado suave
        return ResponseEntity.noContent().build();
    }

    /**
     * Restaura un usuario eliminado lógicamente
     */
    @PatchMapping(ApiRoutes.User.RESTORE)
    public ResponseEntity<Void> restoreUser(@PathVariable Long id) {
        userService.restoreUser(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Verifica si existe un usuario por email
     */
    @GetMapping(ApiRoutes.EXISTS)
    public ResponseEntity<Boolean> userExists(@RequestParam String email) {
        return ResponseEntity.ok(userService.existsByEmail(email));
    }
}
