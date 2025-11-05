package com.tubolsillo.exception.custom;

import com.tubolsillo.constants.ErrorMessages;

/**
 * Excepción que se lanza cuando las credenciales de un usuario no son correctas
 */
public class InvalidCredentialsException extends RuntimeException {

    /**
     * Construye la excepción con un mensaje por defecto
     */
    public InvalidCredentialsException() {
        super(ErrorMessages.INVALID_CREDENTIALS);
    }

    /**
     * Construye la excepción con un mensaje personalizado
     *
     * @param message El mensaje personalizado
     */
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
