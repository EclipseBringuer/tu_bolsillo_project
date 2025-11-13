package com.tubolsillo.security.jwt.blacklist;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Servicio que maneja la lista negra de tokens de acceso en un servicio redis
 */
@Service
@RequiredArgsConstructor
public class RedisBlacklistService {

    private final RedisTemplate<String, String> redisTemplate;
    private static final String BLACKLIST_PREFIX = "BL:";

    /**
     * Añade un Access Token a la lista negra.
     * El TTL (Time To Live) debe ser el tiempo restante de vida del token.
     *
     * @param token             El JWT a invalidar.
     * @param expirationSeconds El tiempo que le queda de vida al token (en segundos).
     */
    public void blacklistToken(String token, long expirationSeconds) {
        if (expirationSeconds > 0) {
            redisTemplate.opsForValue().set(
                    BLACKLIST_PREFIX + token,
                    "invalidated",
                    expirationSeconds,
                    TimeUnit.SECONDS
            );
        }
    }

    /**
     * Comprueba si un token está en la lista negra.
     *
     * @param token El JWT a comprobar.
     * @return true si el token fue invalidado y aún no ha expirado su TTL.
     */
    public boolean isBlacklisted(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(BLACKLIST_PREFIX + token));
    }
}
