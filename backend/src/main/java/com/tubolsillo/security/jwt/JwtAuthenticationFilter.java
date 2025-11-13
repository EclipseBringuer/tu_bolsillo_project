package com.tubolsillo.security.jwt;

import com.tubolsillo.security.jwt.blacklist.RedisBlacklistService;
import com.tubolsillo.security.user.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro de autenticación JWT que se ejecuta una vez por cada petición HTTP.
 * Este filtro intercepta las peticiones entrantes para extraer y validar
 * el token JWT presente en el encabezado 'Authorization'. Si el token es válido,
 * autentica al usuario en el contexto de seguridad de Spring.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * Utilidad para la gestión de tokens JWT (generación, validación, extracción de claims).
     */
    private final JwtUtils jwtUtils;

    /**
     * Servicio personalizado para cargar los detalles del usuario a partir del email.
     */
    private final CustomUserDetailsService userDetailsService;

    /**
     * Servicio de lista negra de tokens
     */
    private final RedisBlacklistService blacklistService;

    /**
     * Realiza el filtrado de la petición HTTP.
     * <p>
     * Esta función realiza los siguientes pasos:
     * <ol>
     * <li>Intenta obtener el token JWT del encabezado 'Authorization' de la petición.</li>
     * <li>Si se encuentra un token y es válido (mediante {@link JwtUtils#validateToken(String)}):
     * <ul>
     * <li>Extrae el email del usuario del token.</li>
     * <li>Carga los detalles del usuario ({@link org.springframework.security.core.userdetails.UserDetails})
     * utilizando {@link CustomUserDetailsService#loadUserByUsername(String)}.</li>
     * <li>Crea un objeto {@link UsernamePasswordAuthenticationToken} con los detalles del usuario y sus autoridades.</li>
     * <li>Establece los detalles de la autenticación web para el token.</li>
     * <li>Establece el objeto de autenticación en él {@link SecurityContextHolder},
     * lo que autentica al usuario para la duración de la petición actual.</li>
     * </ul>
     * </li>
     * <li>Si ocurre alguna excepción durante el proceso (ej. token inválido, usuario no encontrado),
     * la excepción es logueada para depuración y la cadena de filtros continúa,
     * lo que puede resultar en un acceso denegado por parte de Spring Security
     * si el recurso está protegido y no se pudo establecer la autenticación.</li>
     * <li>Continúa la cadena de filtros.</li>
     * </ol>
     *
     * @param request     La petición HTTP entrante.
     * @param response    La respuesta HTTP saliente.
     * @param filterChain La cadena de filtros para continuar el procesamiento.
     * @throws ServletException Si ocurre un error específico de Servlet.
     * @throws IOException      Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");

        String token = jwtUtils.extractTokenIfPresent(authorizationHeader);

        // Se valida que el token no esté vacío y sea válido
        if (token != null && jwtUtils.validateToken(token)) {

            // Se comprueba que el token no esté en la blacklist
            if (blacklistService.isBlacklisted(token)) {
                filterChain.doFilter(request, response);
                return;
            }

            // Se obtiene al usuario del token
            String email = jwtUtils.getEmailFromToken(token);
            var userDetails = userDetailsService.loadUserByUsername(email);

            var auth = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }
}
