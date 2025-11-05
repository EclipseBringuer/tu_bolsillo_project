package com.tubolsillo.exception.custom;

import com.tubolsillo.constants.ErrorMessages;

/**
 * Excepción que salta cuando un rol no se ha encontrado
 */
public class RoleNotFoundException extends RuntimeException {

    /**
     * Construye la excepción con un mensaje por defecto
     */
    public RoleNotFoundException() {
        super(ErrorMessages.ROLE_NOT_FOUND);
    }

    /**
     * Construye la excepción con un mensaje personalizado
     *
     * @param message El mensaje personalizado
     */
    public RoleNotFoundException(String message) {
        super(message);
    }
}
