package com.tubolsillo.exception.custom;

import com.tubolsillo.constants.ErrorMessages;

/**
 * Excepción que se da cuando un nombre de categoría ya está en uso
 */
public class CategoryRepeatedException extends RuntimeException {

    /**
     * Construye la excepción con un mensaje por defecto
     */
    public CategoryRepeatedException() {
        super(ErrorMessages.CATEGORY_REPEATED);
    }

    /**
     * Construye la excepción con un mensaje personalizado
     *
     * @param message El mensaje personalizado
     */
    public CategoryRepeatedException(String message) {
        super(message);
    }
}
