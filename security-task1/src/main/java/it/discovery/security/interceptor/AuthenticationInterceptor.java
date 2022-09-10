package it.discovery.security.interceptor;

import io.jsonwebtoken.*;
import it.discovery.security.NoSecurity;
import it.discovery.security.config.SecurityConfiguration;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final SecurityConfiguration securityConfig;

    private static final String HEADER_PREFIX = "Bearer ";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        if (handler instanceof HandlerMethod handlerMethod) {

            Object restController = handlerMethod.getBean();
            if (restController.getClass().isAnnotationPresent(NoSecurity.class)) {
                return true;
            }

            String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (authHeader == null || authHeader.isBlank()) {
                response.sendError(HttpStatus.UNAUTHORIZED.value());
                return false;
            }

            return validate(authHeader.replaceAll(HEADER_PREFIX, ""));
        }

        return true;
    }

    private SecretKey toSecretKey(String key) {
        byte[] decodedKey = Base64.getDecoder().decode(key);
        return new SecretKeySpec(decodedKey, SignatureAlgorithm.HS512.getJcaName());
    }

    private boolean validate(String authHeader) {
        try {
            Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(toSecretKey(securityConfig.key()))
                    .build().parseClaimsJws(authHeader);

            log.info("User logged in {}", jws.getBody().getSubject());

            return true;
        } catch (JwtException ex) {
            log.error(ex.getMessage(), ex);
            return false;
        }
    }
}
