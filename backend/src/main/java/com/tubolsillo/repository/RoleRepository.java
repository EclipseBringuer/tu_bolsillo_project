package com.tubolsillo.repository;

import com.tubolsillo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para acceder a los datos de la entidad Role
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Busca un rol por su nombre
     *
     * @param name El nombre del rol
     * @return Optional con el rol encontrado o vacío si no existe
     */
    Optional<Role> findByName(String name);
}
