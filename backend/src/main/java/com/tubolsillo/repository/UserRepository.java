package com.tubolsillo.repository;

import com.tubolsillo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para acceder a los datos de la entidad User
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Busca un usuario activo por su email
     *
     * @param email El email del usuario
     * @return Optional con el usuario encontrado, o vacío si no existe
     */
    Optional<User> findByEmailAndDeletedAtIsNull(String email);

    /**
     * Verifica si existe un usuario activo con el email dado
     *
     * @param email El a comprobar
     * @return true si existe, false si no
     */
    boolean existsByEmailAndDeletedAtIsNull(String email);

    /**
     * Busca un usuario activo por su ID
     *
     * @param id El ID del usuario
     * @return Optional con el usuario encontrado
     */
    Optional<User> findByIdAndDeletedAtIsNull(Long id);

    /**
     * Lista todos los usuarios activos
     *
     * @return Lista de usuarios que no están eliminados
     */
    List<User> findAllByDeletedAtIsNull();
}
