package com.tubolsillo.config;

import com.tubolsillo.constants.ApiRoutes;
import com.tubolsillo.constants.Roles;
import com.tubolsillo.security.jwt.JwtAuthenticationFilter;
import com.tubolsillo.security.user.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * Clase de configuración de la seguridad
 */
@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * Filtro de autenticación JWT
     */
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /**
     * Servicio personalizado de manejo de usuarios
     */
    private final CustomUserDetailsService customUserDetailsService;

    /**
     * Configuración del filtro de seguridad
     *
     * @param http La seguridad
     * @return La cadena de filtros de seguridad
     * @throws Exception Si existe un error
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers(
                                        ApiRoutes.Auth.BASE + ApiRoutes.Auth.LOGIN,
                                        ApiRoutes.Auth.BASE + ApiRoutes.Auth.REGISTER,
                                        ApiRoutes.Auth.BASE + ApiRoutes.Auth.REFRESH,
                                        ApiRoutes.API_DOCS,
                                        ApiRoutes.SWAGGER,
                                        ApiRoutes.DOCUMENTATION
                                ).permitAll()
                                .requestMatchers(
                                        ApiRoutes.User.BASE + ApiRoutes.EXISTS,
                                        ApiRoutes.User.BASE + ApiRoutes.BY_ID,
                                        ApiRoutes.User.RESTORE
                                ).hasRole(Roles.ADMIN)
                                .anyRequest().authenticated())
                .userDetailsService(customUserDetailsService)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Bean de generación de encriptadores de contraseña
     *
     * @return El encriptador
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean que devuelve el manejador de autenticación
     *
     * @param configuration La configuración de la autenticación
     * @return El manejador de la autenticación
     * @throws Exception Si hay un error
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        // Se usa en desarrollo el puerto de Angular
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
