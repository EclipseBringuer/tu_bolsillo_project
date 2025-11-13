package com.tubolsillo.security.jwt;

import com.tubolsillo.exception.custom.InvalidTokenFormatException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Colección de funciones para manejar JSON Web Tokens
 */
@Component
public class JwtUtils {

    /**
     * Clave secreta
     */
    @Value("${jwt.secret}")
    private String jwtSecret;

    /**
     * Tiempo de expiración del token
     */
    @Value("${jwt.access.expiration}")
    private long jwtExpirationMs;

    /**
     * Clave secreta para generar los token
     */
    private SecretKey secretKey;

    /**
     * Inicializa la clave secreta cuando se instancia el bean
     */
    @PostConstruct
    public void init() {
        // Decodifica la clave Base64 en bytes
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Genera un token de acceso seguro a un usuario
     *
     * @param email Email del usuario
     * @return El token generado
     */
    public String generateToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .subject(email)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(secretKey)
                .compact();
    }

    /**
     * Obtiene el email del usuario del token
     *
     * @param token El token
     * @return El email del usuario
     */
    public String getEmailFromToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    /**
     * Obtiene la fecha de expiración del token.
     *
     * @param token El token.
     * @return La fecha de expiración (Date) del token.
     */
    public Date getExpirationDateFromToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration();
    }

    /**
     * Comprueba si un token es válido
     *
     * @param token El token a validar
     * @return true si es válido y false si no
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * [USO EN FILTRO JWT]
     * Extrae el JWT de la cabecera. Devuelve la cadena pura del token si está presente y tiene el formato "Bearer ".
     *
     * @param authorizationHeader El valor completo de la cabecera Authorization.
     * @return El JWT sin el prefijo "Bearer ", o null si falta o es incorrecto.
     */
    public String extractTokenIfPresent(String authorizationHeader) {
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer "))
            return authorizationHeader.substring(7);
        return null;
    }

    /**
     * [USO EN CONTROLADORES]
     * Extrae el JWT de la cabecera. Lanza InvalidTokenFormatException si el formato es incorrecto.
     *
     * @param authorizationHeader El valor completo de la cabecera Authorization.
     * @return El JWT sin el prefijo "Bearer ".
     * @throws InvalidTokenFormatException si el token no está presente o no tiene el formato "Bearer ".
     */
    public String extractTokenOrThrow(String authorizationHeader) {
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer "))
            return authorizationHeader.substring(7);
        throw new InvalidTokenFormatException();
    }
}
