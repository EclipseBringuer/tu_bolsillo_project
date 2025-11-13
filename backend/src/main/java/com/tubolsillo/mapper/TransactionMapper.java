package com.tubolsillo.mapper;

import com.tubolsillo.dto.TransactionDTO;
import com.tubolsillo.entity.Transaction;
import org.mapstruct.Mapper;

/**
 * Conversor de la entidad Transaction
 */
@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface TransactionMapper {

    /**
     * Convierte la transacción a DTO de información
     *
     * @param transaction La transacción a convertir
     * @return El DTO informativo
     */
    TransactionDTO toDTO(Transaction transaction);
}
