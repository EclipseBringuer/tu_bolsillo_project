package com.tubolsillo.service;

import com.tubolsillo.dto.AuthResponse;
import com.tubolsillo.dto.LoginRequest;
import com.tubolsillo.dto.RefreshRequest;
import com.tubolsillo.dto.RegisterRequest;
import com.tubolsillo.entity.RefreshToken;
import com.tubolsillo.entity.Role;
import com.tubolsillo.entity.User;
import com.tubolsillo.exception.custom.EmailAlreadyInUseException;
import com.tubolsillo.exception.custom.InvalidCredentialsException;
import com.tubolsillo.exception.custom.RoleNotFoundException;
import com.tubolsillo.exception.custom.UserNotFoundException;
import com.tubolsillo.repository.RoleRepository;
import com.tubolsillo.repository.UserRepository;
import com.tubolsillo.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

/**
 * Controlador encargado de las funciones de autenticación de usuarios en el sistema
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RefreshTokenService refreshTokenService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    /**
     * Realiza el inicio de sesión de un usuario
     *
     * @param request La petición de inicio de sesión
     * @return Respuesta autorizada
     */
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmailAndDeletedAtIsNull(request.email())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        log.info("El usuario '{}' ha iniciado sesión correctamente.", user.getEmail());

        String accessToken = jwtUtils.generateToken(user.getEmail());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return new AuthResponse(accessToken, refreshToken.getToken());
    }

    /**
     * Registra un nuevo usuario en el sistema
     *
     * @param request Petición de registro con los datos del nuevo usuario
     * @return El token del usuario registrado
     */
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmailAndDeletedAtIsNull(request.email())) {
            log.warn("Intento de registro con email ya en uso: {}", request.email());
            throw new EmailAlreadyInUseException();
        }

        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> {
                    log.error("No se encontró el rol 'USER' en el sistema.");
                    return new RoleNotFoundException();
                });

        User user = new User();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRoles(Collections.singleton(userRole));

        // Se guarda el usuario para obtener su ID
        userRepository.save(user);
        log.info("Usuario '{}' registrado exitosamente con ID: {}", user.getEmail(), user.getId());

        String accessToken = jwtUtils.generateToken(user.getEmail());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return new AuthResponse(accessToken, refreshToken.getToken());
    }

    /**
     * Refresca el token de acceso cuando este ha caducado
     *
     * @param requestToken El token de refresco
     * @return Los nuevos tokens
     */
    @Transactional
    public AuthResponse refresh(RefreshRequest requestToken) {
        RefreshToken refreshToken = refreshTokenService.findByToken(requestToken.refreshToken());
        User user = refreshToken.getUser();

        // Se genera un nuevo token de acceso
        String accessToken = jwtUtils.generateToken(user.getEmail());

        // Se cambia por seguridad el RefreshToken
        RefreshToken newRefreshToken = refreshTokenService.createRefreshToken(user);

        log.info("Usuario '{}' ha refrescado sus tokens", user.getEmail());

        return new AuthResponse(accessToken, newRefreshToken.getToken());
    }

    /**
     * Cierra la sesión del usuario
     */
    @Transactional
    public void logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmailAndDeletedAtIsNull(email).orElseThrow(UserNotFoundException::new);
        refreshTokenService.deleteByUser(user);
        log.info("Usuario '{}' ha cerrado sesión y eliminado sus tokens", email);
    }
}
