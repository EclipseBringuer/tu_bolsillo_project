package com.tubolsillo.exception;

import com.tubolsillo.dto.ErrorResponse;
import com.tubolsillo.exception.custom.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

/**
 * Global exception handler para manejar las excepciones lanzadas en la aplicación.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Crea un ErrorResponse con los valores pasados por parámetro
     *
     * @param status  El código de estado HTTP
     * @param message El mensaje del error
     * @return Un ErrorResponse con la información
     */
    private ErrorResponse buildErrorResponse(HttpStatus status, String message) {
        log.error(message);
        return new ErrorResponse(status.value(), message, LocalDateTime.now());
    }

    /**
     * Maneja la excepción InvalidCredentialsException y devuelve un error 401.
     *
     * @param ex Excepción lanzada cuando las credenciales son incorrectas.
     * @return ResponseEntity con detalles del error.
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        return new ResponseEntity<>(buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage()),
                HttpStatus.UNAUTHORIZED);
    }

    /**
     * Maneja la excepción EmailAlreadyInUseException y devuelve un error 409.
     *
     * @param ex Excepción lanzada cuando el email ya está en uso.
     * @return ResponseEntity con detalles del error.
     */
    @ExceptionHandler(EmailAlreadyInUseException.class)
    public ResponseEntity<ErrorResponse> handleEmailAlreadyInUseException(EmailAlreadyInUseException ex) {
        return new ResponseEntity<>(buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage()), HttpStatus.CONFLICT);
    }

    /**
     * Maneja la excepción TokenExpiredException y devuelve un 401
     *
     * @param ex Excepción que se lanza cuando el token utilizado ha expirado
     * @return ResponseEntity con detalles del error.
     */
    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ErrorResponse> handleTokenExpiredException(TokenExpiredException ex) {
        return new ResponseEntity<>(buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    /**
     * Maneja la excepción NoResourceFoundException y devuelve un error 404.
     *
     * @param ex Excepción lanzada cuando un recurso no se ha encontrado.
     * @return ResponseEntity con detalles del error.
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException ex) {
        return new ResponseEntity<>(buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja la excepción InvalidTokenFormatException y devuelve un error 401.
     *
     * @param ex Excepción que se lanza cuando el bearer token no tiene el formato correcto
     * @return ResponseEntity con detalles del error.
     */
    @ExceptionHandler(InvalidTokenFormatException.class)
    public ResponseEntity<ErrorResponse> handleInvalidTokenFormatException(InvalidTokenFormatException ex) {
        return new ResponseEntity<>(buildErrorResponse(HttpStatus.UNAUTHORIZED, ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    /**
     * Maneja la excepción CategoryRepeatedException y devuelve un error 409.
     *
     * @param ex Excepción que se lanza cuando un usuario intenta crear una categoría igual a una ya creada por el mismo
     * @return ResponseEntity con detalles del error.
     */
    @ExceptionHandler(CategoryRepeatedException.class)
    public ResponseEntity<ErrorResponse> handleCategoryRepeatedException(CategoryRepeatedException ex) {
        return new ResponseEntity<>(buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage()), HttpStatus.CONFLICT);
    }

    /**
     * Maneja la excepción ResourceNotFoundException y devuelve un error 404
     *
     * @param ex Excepción que se lanza cuando un recurso no se ha encontrado en el sistema
     * @return ResponseEntity con los detalles del error
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    /**
     * Maneja la excepción MethodArgumentNotValidException y devuelve un error 400
     *
     * @param ex Excepción lanzada cuando se envían parámetros inválidos
     * @return ResponseEntity con detalles del error
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        return new ResponseEntity<>(
                buildErrorResponse(HttpStatus.BAD_REQUEST, message),
                HttpStatus.BAD_REQUEST
        );
    }

    /**
     * Maneja cualquier otra excepción no controlada y devuelve un error 500.
     *
     * @return ResponseEntity con detalles del error.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllException(Exception ex) {
        return new ResponseEntity<>(buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocurrió un error inesperado. Intente más tarde: " + ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
