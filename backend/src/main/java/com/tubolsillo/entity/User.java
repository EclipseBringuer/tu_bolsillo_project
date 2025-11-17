package com.tubolsillo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Set;

/**
 * Representa un usuario en el sistema
 */
@Entity
@Table(name = "user")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * Identificador único del usuario
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre del usuario
     */
    @Column(nullable = false, name = "first_name")
    private String firstName;

    /**
     * Apellidos del usuario
     */
    @Column(nullable = false, name = "last_name")
    private String lastName;

    /**
     * Correo electrónico del usuario (único)
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Contraseña encriptada del usuario
     */
    @Column(nullable = false)
    private String password;

    /**
     * Fecha de creación del usuario
     */
    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    /**
     * Fecha de la última actualización del usuario
     */
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    /**
     * Fecha de eliminación lógica del usuario (null si no ha sido eliminado).
     */
    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    /**
     * Roles asignados al usuario
     */
    @ManyToMany(fetch =  FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    /**
     * Función que se ejecuta automáticamente antes de insertar un nuevo usuario.
     * Establece las fechas de creación y actualización.
     */
    @PrePersist
    public void onCreate() {
        Timestamp now = Timestamp.from(Instant.now());
        this.createdAt = now;
        this.updatedAt = now;
    }

    /**
     * Función que se ejecuta automáticamente antes de actualizar un usuario.
     * Actualiza la fecha de modificación.
     */
    @PreUpdate
    public void onUpdate() {
        this.updatedAt = Timestamp.from(Instant.now());
    }
}
