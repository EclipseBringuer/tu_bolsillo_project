package com.tubolsillo.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Representa un rol de usuario en el sistema
 */
@Entity
@Table(name = "role")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    /**
     * Identificador único del rol
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del rol
     */
    @Column(nullable = false, unique = true)
    private String name;
}
