package com.tubolsillo.constants;

/**
 * Clase que contiene todos los mensajes de error
 */
public final class ErrorMessages {

    /**
     * Constructor privado para evitar instancias
     */
    private ErrorMessages() {
    }

    public static final String USER_NOT_FOUND = "El usuario no existe o ha sido eliminado";
    public static final String INVALID_CREDENTIALS = "Credenciales de usuario incorrectas";
    public static final String EMAIL_ALREADY_IN_USE = "El email especificado ya está en uso";
    public static final String ROLE_NOT_FOUND = "No se ha encontrado el rol del usuario";
    public static final String TOKEN_EXPIRATION = "El token ha expirado";
    public static final String INVALID_TOKEN_FORMAT = "El bearer token no es valido";
}
