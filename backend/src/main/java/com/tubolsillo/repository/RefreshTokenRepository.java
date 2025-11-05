package com.tubolsillo.repository;

import com.tubolsillo.entity.RefreshToken;
import com.tubolsillo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para acceder a los datos de la entidad RefreshToken
 */
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    /**
     * Devuelve un RefreshToken por su valor
     *
     * @param token El valor del RefreshToken
     * @return Un Optional con el RefreshToken
     */
    Optional<RefreshToken> findByToken(String token);

    /**
     * Borra un token por su usuario asociado
     *
     * @param user El usuario asociado
     */
    void deleteByUser(User user);
}
