package com.tubolsillo.repository;

import com.tubolsillo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para acceder a los datos de la entidad Category
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
