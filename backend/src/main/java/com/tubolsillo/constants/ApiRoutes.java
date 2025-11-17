package com.tubolsillo.constants;

/**
 * Clase que contiene las rutas de la API
 */
public final class ApiRoutes {

    /**
     * Constructor privado para evitar instancias
     */
    private ApiRoutes() {
    }

    /**
     * Ruta base de la API
     */
    public static final String API = "/api";

    /**
     * Rutas Comunes
     */
    public static final String BY_ID = "/{id}";
    public static final String BY_NAME = "/{name}";
    public static final String EXISTS = "/exists";
    public static final String ALL = "/all";

    /**
     * Documentación
     */
    public static final String DOCUMENTATION = API + "/docs/**";
    public static final String API_DOCS = "/v3/api-docs/**";
    public static final String SWAGGER = API + "/swagger-ui/**";

    /**
     * Clase estática con las rutas del controlador de autorización
     */
    public static final class Auth {
        /**
         * Constructor privado para evitar instancias
         */
        private Auth() {
        }

        /**
         * Ruta base del controlador
         */
        public static final String BASE = API + "/auth";
        public static final String LOGIN = "/login";
        public static final String REGISTER = "/register";
        public static final String REFRESH = "/refresh";
        public static final String LOGOUT = "/logout";
        public static final String RESET_PASSWORD = "/reset-password";
    }

    /**
     * Clase estática con las rutas del controlador de usuarios
     */
    public static final class User {
        /**
         * Constructor privado para evitar instancias
         */
        private User() {
        }

        /**
         * Ruta base del controlador
         */
        public static final String BASE = API + "/user";
        public static final String RESTORE = "/restore" + BY_ID;
        public static final String ME = "/me";
    }

    /**
     * Clase estática con las rutas del controlador de categorías
     */
    public static final class Category {
        /**
         * Constructor privado para evitar instancias
         */
        private Category() {
        }

        /**
         * Ruta base del controlador
         */
        public static final String BASE = API + "/category";
    }

    /**
     * Clase estática con las rutas del controlador de transacciones
     */
    public static final class Transaction {
        /**
         * Constructor privado para evitar instancias
         */
        private Transaction() {
        }

        /**
         * Ruta base del controlador
         */
        public static final String BASE = API + "/transaction";
    }
}
