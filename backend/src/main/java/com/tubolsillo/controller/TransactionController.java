package com.tubolsillo.controller;

import com.tubolsillo.constants.ApiRoutes;
import com.tubolsillo.dto.CreateTransactionDTO;
import com.tubolsillo.dto.TransactionDTO;
import com.tubolsillo.service.TransactionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador encargado de recibir las peticiones relacionadas con Transaction
 */
@RestController
@RequestMapping(ApiRoutes.Transaction.BASE)
@AllArgsConstructor
public class TransactionController {

    /**
     * Servicio de la entidad transaction
     */
    private final TransactionService transactionService;

    /**
     * Devuelve las transacciones del usuario que realiza la petición
     *
     * @return El listado con las transacciones del usuario
     */
    @GetMapping
    public ResponseEntity<List<TransactionDTO>> getCurrentUserTransactions() {
        return ResponseEntity.ok(transactionService.getCurrentUserTransactions());
    }

    /**
     * Crea una transacción nueva para el usuario que realiza la petición
     *
     * @param transactionDTO La información de la transacción a crear
     * @return La transacción ya creada
     */
    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@Valid @RequestBody CreateTransactionDTO transactionDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.saveTransaction(transactionDTO));
    }
}
