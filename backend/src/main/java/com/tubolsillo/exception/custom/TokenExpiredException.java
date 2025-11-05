package com.tubolsillo.exception.custom;

import com.tubolsillo.constants.ErrorMessages;

/**
 * Excepción que se lanza cuando un token ha expirado
 */
public class TokenExpiredException extends RuntimeException {

    /**
     * Construye la excepción con un mensaje por defecto
     */
    public TokenExpiredException() {
        super(ErrorMessages.TOKEN_EXPIRATION);
    }

    /**
     * Construye la excepción con un mensaje personalizado
     *
     * @param message El mensaje personalizado
     */
    public TokenExpiredException(String message) {
        super(message);
    }
}
