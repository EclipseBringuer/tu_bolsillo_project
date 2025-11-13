package com.tubolsillo.controller;

import com.tubolsillo.constants.ApiRoutes;
import com.tubolsillo.dto.TransactionDTO;
import com.tubolsillo.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @GetMapping(ApiRoutes.CURRENT)
    public ResponseEntity<List<TransactionDTO>> getCurrentUserTransactions() {
        return ResponseEntity.ok(transactionService.getCurrentUserTransactions());
    }
}
