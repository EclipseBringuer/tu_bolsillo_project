package com.tubolsillo.controller;

import com.tubolsillo.constants.ApiRoutes;
import com.tubolsillo.dto.CreateTransactionDTO;
import com.tubolsillo.dto.CustomPageResponse;
import com.tubolsillo.dto.TransactionDTO;
import com.tubolsillo.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador encargado de recibir las peticiones relacionadas con Transaction
 */
@RestController
@RequestMapping(ApiRoutes.Transaction.BASE)
@Tag(name = "Transacciones", description = "Operaciones para crear, buscar y gestionar transacciones.")
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
    @Operation(
            summary = "Obtener transacciones del usuario",
            description = "Obtiene las transacciones del usuario que realiza la petición"
    )
    @GetMapping
    public ResponseEntity<CustomPageResponse<TransactionDTO>> getCurrentUserTransactions(
            @Parameter(description = "Paginación y ordenación: ?page=0&size=20&sort=date,desc")
            Pageable pageable) {
        var transactionsPage = transactionService.getCurrentUserTransactions(pageable);
        return ResponseEntity.ok(new CustomPageResponse<>(transactionsPage));
    }

    /**
     * Crea una transacción nueva para el usuario que realiza la petición
     *
     * @param transactionDTO La información de la transacción a crear
     * @return La transacción ya creada
     */
    @Operation(
            summary = "Crear Transacción",
            description = "Crea una nueva transacción asociada al usuario que realiza la petición"
    )
    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@Valid @RequestBody CreateTransactionDTO transactionDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.saveTransaction(transactionDTO));
    }

    /**
     * Obtiene una transacción por su identificador
     *
     * @param id El identificador de la transacción
     * @return La transacción
     */
    @Operation(
            summary = "Obtener Transacción",
            description = "Obtiene la información de una transacción por su identificador"
    )
    @GetMapping(ApiRoutes.BY_ID)
    public ResponseEntity<TransactionDTO> getTransactionById(
            @Parameter(description = "Identificador único de la transacción (Long)", required = true)
            @PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }

    /**
     * Elimina una transacción por su identificador
     *
     * @param id El identificador de la transacción
     * @return Una respuesta vacía
     */
    @Operation(
            summary = "Borrar Transacción",
            description = "Elimina una transacción por su identificador"
    )
    @DeleteMapping(ApiRoutes.BY_ID)
    public ResponseEntity<Void> deleteTransaction(
            @Parameter(description = "Identificador único de la transacción (Long)", required = true)
            @PathVariable Long id) {
        transactionService.deleteTransaction(id);
        return ResponseEntity.noContent().build();
    }
}
