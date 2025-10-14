package ru.itmo.cs.dandadan.magicitems.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.util.StringUtils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private static final int MAX_TOKEN_LENGTH = 4096;

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        String header = request.getHeader("Authorization");
        String token = null;
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            token = header.substring(7).trim();
        }

        if (token != null) {
            if (token.isEmpty() || token.length() > MAX_TOKEN_LENGTH || token.contains("\r") || token.contains("\n")) {
                logger.debug("Rejected Authorization token due to invalid format/length");
            } else {
                try {
                    String username = jwtUtils.validateAndExtractUsername(token);
                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                        UsernamePasswordAuthenticationToken auth =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                } catch (ExpiredJwtException eje) {
                    logger.debug("JWT expired");
                } catch (JwtException | IllegalArgumentException e) {
                    logger.debug("Invalid ");
                } catch (Exception e) {
                    logger.error("Unexpected error while processing JWT", e);
                }
            }
        }
        chain.doFilter(request, response);
    }
}