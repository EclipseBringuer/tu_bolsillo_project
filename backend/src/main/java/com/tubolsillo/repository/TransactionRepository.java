package com.tubolsillo.repository;

import com.tubolsillo.entity.Category;
import com.tubolsillo.entity.Transaction;
import com.tubolsillo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para acceder a los datos de la entidad Transaction
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Obtiene un listado de transacciones pertenecientes a una categoría
     *
     * @param category La categoría a filtrar
     * @return El listado de transacciones coincidentes
     */
    List<Transaction> findAllByCategory(Category category);

    /**
     * Obtiene el listado de transacciones realizadas por un usuario
     * @param user El usuario de las transacciones
     * @return El listado de transacciones coincidentes
     */
    List<Transaction> findAllByUser(User user);
}
