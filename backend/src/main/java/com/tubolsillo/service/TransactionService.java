package com.tubolsillo.service;

import com.tubolsillo.dto.TransactionDTO;
import com.tubolsillo.mapper.TransactionMapper;
import com.tubolsillo.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
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
     * Conversor de la entidad transaction
     */
    private final TransactionMapper transactionMapper;

    /**
     * Obtiene las transacciones del usuario que realiza la petición
     *
     * @return El listado de transacciones realizadas por el usuario
     */
    public List<TransactionDTO> getCurrentUserTransactions() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        log.info("Obteniendo las transacciones del usuario '{}'", email);
        var transactions = transactionRepository.findAllByUserEmail(email);
        return transactions.stream().map(transactionMapper::toDTO).toList();
    }
}
