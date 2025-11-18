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

    /**
     * Verifica si ya existe una categoría con el mismo usuario y el mismo nombre
     *
     * @param email El email del usuario
     * @param name  El nombre de la categoría
     * @return Si existe o no
     */
    boolean existsByUserEmailAndName(String email, String name);
}
