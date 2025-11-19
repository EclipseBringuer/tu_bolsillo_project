package com.tubolsillo.service;

import com.tubolsillo.dto.CreateTransactionDTO;
import com.tubolsillo.dto.TransactionDTO;
import com.tubolsillo.entity.Category;
import com.tubolsillo.entity.Transaction;
import com.tubolsillo.entity.User;
import com.tubolsillo.exception.custom.ResourceNotFoundException;
import com.tubolsillo.mapper.TransactionMapper;
import com.tubolsillo.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio con las funcionalidades relacionadas con la entidad Transaction
 */
@Service
@AllArgsConstructor
@Slf4j
public class TransactionService {

    /**
     * Repositorio de la entidad Transaction
     */
    private final TransactionRepository transactionRepository;

    /**
     * Servicio de la entidad User
     */
    private final UserService userService;

    /**
     * Servicio de la entidad Category
     */
    private final CategoryService categoryService;

    /**
     * Conversor de la entidad transaction
     */
    private final TransactionMapper transactionMapper;

    /**
     * Obtiene las transacciones del usuario que realiza la petición
     *
     * @return El listado de transacciones realizadas por el usuario
     */
    public List<TransactionDTO> getCurrentUserTransactions() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Obteniendo las transacciones del usuario '{}'", email);
        var transactions = transactionRepository.findAllByUserEmail(email);
        return transactions.stream().map(transactionMapper::toDTO).toList();
    }

    /**
     * Registra una nueva transacción para el usuario que realiza la petición
     *
     * @param transactionDTO La información de la transacción a guardar
     * @return La transacción ya creada
     */
    public TransactionDTO saveTransaction(CreateTransactionDTO transactionDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Iniciando la creación de la transacción para el usuario '{}'", email);

        // Se obtiene al usuario actual
        User user = userService.findByEmail(email);

        // Se obtiene la categoría y se verifica que pertenezca al usuario
        Category category = categoryService.findCategoryById(transactionDTO.categoryId());

        if (!category.getUser().getId().equals(user.getId())) {
            log.error("La categoría con ID={} no pertenece al usuario '{}'", category.getId(), email);
            throw new ResourceNotFoundException("La categoría especificada no fue encontrada");
        }

        // Se crea la transacción
        Transaction newTransaction = Transaction.builder()
                .user(user)
                .category(category)
                .amount(transactionDTO.amount())
                .description(transactionDTO.description())
                .transactionDate(transactionDTO.transactionDate())
                .build();

        // Se guarda la transacción
        transactionRepository.save(newTransaction);
        log.info("Transacción creada con éxito con ID={}", newTransaction.getId());

        // Devolver DTO de respuesta
        return transactionMapper.toDTO(newTransaction);
    }
}
