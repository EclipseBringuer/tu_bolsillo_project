package com.tubolsillo.exception.custom;

import com.tubolsillo.constants.ErrorMessages;

/**
 * Excepción que se da cuando un email ya está en uso
 */
public class EmailAlreadyInUseException extends RuntimeException {

    /**
     * Construye la excepción con un mensaje por defecto
     */
    public EmailAlreadyInUseException() {
        super(ErrorMessages.EMAIL_ALREADY_IN_USE);
    }

    /**
     * Construye la excepción con un mensaje personalizado
     *
     * @param message El mensaje personalizado
     */
    public EmailAlreadyInUseException(String message) {
        super(message);
    }
}
