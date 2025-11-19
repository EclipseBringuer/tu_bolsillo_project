package com.tubolsillo.service;

import com.tubolsillo.dto.UserDTO;
import com.tubolsillo.entity.User;
import com.tubolsillo.exception.custom.ResourceNotFoundException;
import com.tubolsillo.mapper.UserMapper;
import com.tubolsillo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

/**
 * Servicio con las funcionalidades relacionadas con la entidad User
 */
@Service
@RequiredArgsConstructor
public class UserService {

    /**
     * Repositorio de la entidad User
     */
    private final UserRepository userRepository;

    /**
     * Conversor de la entidad User
     */
    private final UserMapper userMapper;

    /**
     * Devuelve la información del usuario actual
     *
     * @return DTO de usuario con la información del usuario actual
     */
    public UserDTO getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = findByEmail(email);

        return userMapper.toUserDTO(user);
    }

    /**
     * Obtiene un usuario por su email
     *
     * @param email El email del usuario
     * @return El usuario encontrado
     */
    public User findByEmail(String email) {
        return userRepository.findByEmailAndDeletedAtIsNull(email)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario '" + email + "' no existe."));
    }

    /**
     * Verifica si un usuario existe por su email
     *
     * @param email El email a verificar
     * @return true si existe o false si no
     */
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmailAndDeletedAtIsNull(email);
    }

    /**
     * Obtiene un usuario activo por su ID
     *
     * @param id El identificador del usuario
     * @return El usuario activo encontrado
     */
    public User findById(Long id) {
        return userRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario con ID=" + id + " no existe."));
    }

    /**
     * Lista todos los usuarios que no han sido eliminados
     *
     * @return Lista de usuarios activos
     */
    public List<User> findAll() {
        return userRepository.findAllByDeletedAtIsNull();
    }

    /**
     * Elimina a un usuario de forma lógica
     *
     * @param id El ID del usuario
     */
    public void softDeleteUser(Long id) {
        User user = findById(id); // Solo usuarios activos
        user.setDeletedAt(Timestamp.from(Instant.now()));
        userRepository.save(user);
    }

    /**
     * Restaura un usuario eliminado lógicamente
     *
     * @param id El identificador del usuario
     */
    public void restoreUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("El usuario con ID=" + id + " no existe."));
        user.setDeletedAt(null);
        userRepository.save(user);
    }
}
