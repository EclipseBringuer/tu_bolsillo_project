package com.tubolsillo.security.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
}
