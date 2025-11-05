package com.tubolsillo.exception.custom;

import com.tubolsillo.constants.ErrorMessages;

/**
 * Excepción que se lanza cuando un usuario no se ha encontrado en la BD
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Construye la excepción con un mensaje por defecto
     */
    public UserNotFoundException() {
        super(ErrorMessages.USER_NOT_FOUND);
    }

    /**
     * Construye la excepción con un mensaje personalizado
     *
     * @param message El mensaje personalizado
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}