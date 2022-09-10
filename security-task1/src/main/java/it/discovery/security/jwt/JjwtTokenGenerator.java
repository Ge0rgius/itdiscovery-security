package it.discovery.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

@Component
public class JjwtTokenGenerator implements TokenGenerator {

    private final String secretKey;

    public JjwtTokenGenerator(Environment env) {
        secretKey = env.getRequiredProperty("secret.key");
    }

    @Override
    public String generate(String subject, LocalDateTime expirationTime) {
        return Jwts.builder().setSubject(subject)
                .setExpiration(Date.from(expirationTime.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(toSecretKey(secretKey))
                .compact();
    }

    private SecretKey toSecretKey(String key) {
        byte[] decodedKey = Base64.getDecoder().decode(key);
        return new SecretKeySpec(decodedKey, SignatureAlgorithm.HS512.getJcaName());
    }
}
