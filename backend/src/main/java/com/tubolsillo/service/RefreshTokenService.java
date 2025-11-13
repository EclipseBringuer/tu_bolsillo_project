package com.tubolsillo.service;

import com.tubolsillo.entity.RefreshToken;
import com.tubolsillo.entity.User;
import com.tubolsillo.exception.custom.TokenExpiredException;
import com.tubolsillo.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

/**
 * Servicio encargado de manejar la entidad RefreshToken
 */
@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    /**
     * Repositorio de la entidad RefreshToken
     */
    private final RefreshTokenRepository refreshTokenRepository;

    /**
     * Tiempo de expiración de los tokens de refresco
     */
    @Value("${jwt.refresh.expiration}")
    private long refreshExpirationMs;

    /**
     * Crea un nuevo RefreshToken para un usuario
     *
     * @param user El usuario del token
     * @return El RefreshToken para ese usuario
     */
    public RefreshToken createRefreshToken(User user) {
        // Si ya tenía uno, se elimina
        refreshTokenRepository.deleteByUser(user);

        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString()) // Token aleatorio guardado en DB
                .expiryDate(Instant.now().plusMillis(refreshExpirationMs))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    /**
     * Borra un refreshToken por su usuario
     *
     * @param user El usuario con el token
     */
    public void deleteByUser(User user) {
        refreshTokenRepository.deleteByUser(user);
    }

    /**
     * Elimina el refresh token
     *
     * @param refreshToken El token a borrar
     */
    public void deleteRefreshToken(RefreshToken refreshToken) {
        refreshTokenRepository.delete(refreshToken);
    }

    /**
     * Verifica la expiración del token
     *
     * @param token El token a verificar
     * @return El token si ha ido bien
     */
    private RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new TokenExpiredException();
        }
        return token;
    }

    /**
     * Encuentra un RefreshToken por su valor
     *
     * @param token El valor del token
     * @return El token si ha sido encontrado
     */
    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .map(this::verifyExpiration)
                .orElseThrow(TokenExpiredException::new);
    }
}
