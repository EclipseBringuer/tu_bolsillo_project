package com.tubolsillo.exception.custom;

import com.tubolsillo.constants.ErrorMessages;

/**
 * Excepción que se da cuando un recurso no se ha encontrado
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Construye la excepción con un mensaje por defecto
     */
    public ResourceNotFoundException() {
        super(ErrorMessages.RESOURCE_NOT_FOUND);
    }

    /**
     * Construye la excepción con un mensaje personalizado
     * @param message El mensaje personalizado
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
