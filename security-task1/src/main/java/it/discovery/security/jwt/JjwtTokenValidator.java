package it.discovery.security.jwt;

import io.jsonwebtoken.*;
import it.discovery.security.config.SecurityConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class JjwtTokenValidator implements TokenValidator {

    private static final String HEADER_PREFIX = "Bearer ";

    private final SecurityConfiguration securityConfig;

    @Override
    public String validate(String authHeader) {
        if (authHeader == null || authHeader.isBlank()) {
            throw new IllegalArgumentException("Authorization header is absent");
        }
        String token = authHeader.replaceAll(HEADER_PREFIX, "");
        try {
            Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(toSecretKey(securityConfig.key()))
                    .build().parseClaimsJws(token);
            return jws.getBody().getSubject();

        } catch (JwtException ex) {
            throw new RuntimeException(ex);
        }
    }

    private SecretKey toSecretKey(String key) {
        byte[] decodedKey = Base64.getDecoder().decode(key);
        return new SecretKeySpec(decodedKey, SignatureAlgorithm.HS512.getJcaName());
    }

}
