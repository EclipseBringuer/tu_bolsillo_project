package com.tubolsillo.repository;

import com.tubolsillo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para acceder a los datos de la entidad Category
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * Obtiene el listado de categorías pertenecientes a un usuario
     *
     * @param email El email del usuario
     * @return El listado de categorías
     */
    List<Category> findAllByUserEmail(String email);
}
