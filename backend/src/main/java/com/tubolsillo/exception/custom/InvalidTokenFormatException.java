package com.tubolsillo.exception.custom;

import com.tubolsillo.constants.ErrorMessages;

/**
 * Excepción que salta cuando no se ha colocado el Bearer token de forma correcta
 */
public class InvalidTokenFormatException extends RuntimeException {

    /**
     * Construye la excepción con un mensaje por defecto
     */
    public InvalidTokenFormatException() {
        super(ErrorMessages.INVALID_TOKEN_FORMAT);
    }

    /**
     * Construye la excepción con un mensaje personalizado
     *
     * @param message El mensaje personalizado
     */
    public InvalidTokenFormatException(String message) {
        super(message);
    }
}
