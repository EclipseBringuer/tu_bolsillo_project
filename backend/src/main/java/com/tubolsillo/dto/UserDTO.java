package com.tubolsillo.dto;

import java.util.List;

/**
 * DTO con la información de un usuario a mostrar
 *
 * @param firstName El nombre
 * @param lastName  Los apellidos
 * @param email     El email
 * @param roles     Lista con los roles del usuario
 */
public record UserDTO(String firstName, String lastName, String email, List<String> roles) {
}
