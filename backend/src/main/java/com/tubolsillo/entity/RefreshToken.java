package com.tubolsillo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

/**
 * Representa un token de refresco en el sistema
 */
@Entity
@Table(name = "refresh_token")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

    @Column(nullable = false, unique = true, length = 36)
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;
}
