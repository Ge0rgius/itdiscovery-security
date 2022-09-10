package it.discovery.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import it.discovery.security.config.SecurityConfiguration;
import lombok.RequiredArgsConstructor;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
public class JjwtTokenGenerator implements TokenGenerator {

    private final SecurityConfiguration securityConfig;

    @Override
    public String generate(String subject, LocalDateTime expirationTime) {
        return Jwts.builder().setSubject(subject)
                .setExpiration(Date.from(expirationTime.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(toSecretKey(securityConfig.signKey()))
                .compact();
    }

    private SecretKey toSecretKey(String key) {
        byte[] decodedKey = Base64.getDecoder().decode(key);
        return new SecretKeySpec(decodedKey, SignatureAlgorithm.HS512.getJcaName());
    }
}
