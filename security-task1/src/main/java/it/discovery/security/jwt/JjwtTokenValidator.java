package it.discovery.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import it.discovery.security.config.SecurityConfiguration;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JjwtTokenValidator extends BaseTokenValidator implements TokenValidator {

    private final SecurityConfiguration securityConfig;

    @Override
    public String validate(String authHeader) {
        String token = extractToken(authHeader);
        try {
            Jws<Claims> jws = Jwts.parserBuilder().setSigningKey(toSecretKey(securityConfig.key()))
                    .build().parseClaimsJws(token);
            return jws.getBody().getSubject();

        } catch (JwtException ex) {
            throw new RuntimeException(ex);
        }
    }

}
