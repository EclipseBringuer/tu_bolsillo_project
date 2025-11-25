package com.tubolsillo.entity;

import com.tubolsillo.entity.enums.Type;
import jakarta.persistence.*;
import lombok.*;

/**
 * Entidad que representa una categoría de transacción
 */
@Entity
@Table(name = "category")
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    /**
     * Identificador único de la categoría
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Type type;
}
