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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
    public Page<TransactionDTO> getCurrentUserTransactions(Pageable pageable) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("Obteniendo las transacciones del usuario '{}'", email);
        var transactionsPage = transactionRepository.findAllByUserEmail(email, pageable);
        return transactionsPage.map(transactionMapper::toDTO);
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

    /**
     * Obtiene una transacción mediante su identificador
     *
     * @param id El identificador de la transacción
     * @return La información de la transacción
     */
    public TransactionDTO getTransactionById(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        // Se obtiene al usuario actual
        User user = userService.findByEmail(email);

        // Se obtiene la transacción y se verifica que exista y pertenezca al usuario que realiza la petición
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("La transacción con ID=" + id + " no existe"));

        if (!transaction.getUser().getId().equals(user.getId())) {
            log.error("La transacción con ID={} no pertenece al usuario '{}'", transaction.getId(), email);
            throw new ResourceNotFoundException("La transacción con ID=" + id + " no se ha encontrado");
        }

        // Se devuelve la transacción en formato DTO
        return transactionMapper.toDTO(transaction);
    }

    /**
     * Elimina una transacción mediante su identificador
     *
     * @param id El identificador de la transacción
     */
    public void deleteTransaction(Long id) {
        var transaction = getTransactionById(id);
        log.info("Borrando la transacción con ID={}", id);
        transactionRepository.deleteById(transaction.id());
        log.info("La transacción con ID={} ha sido eliminada correctamente", id);
    }
}
