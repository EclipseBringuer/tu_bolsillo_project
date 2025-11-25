package com.tubolsillo.dto;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Record para un DTO de respuesta de paginación personalizado.
 */
public record CustomPageResponse<T>(
        List<T> content,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages
) {
    /**
     * Constructor customizado para mapear el objeto Page<T> de Spring
     *
     * @param springPage Una página de Spring
     */
    public CustomPageResponse(Page<T> springPage) {
        this(
                springPage.getContent(),
                springPage.getNumber(),
                springPage.getSize(),
                springPage.getTotalElements(),
                springPage.getTotalPages()
        );
    }
}
