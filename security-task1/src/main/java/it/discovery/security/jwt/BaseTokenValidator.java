package it.discovery.security.jwt;

import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public abstract class BaseTokenValidator {

    private static final String HEADER_PREFIX = "Bearer ";

    protected String extractToken(String authHeader) {
        if (authHeader == null || authHeader.isBlank()) {
            throw new IllegalArgumentException("Authorization header is absent");
        }
        return authHeader.replaceAll(HEADER_PREFIX, "");
    }

    protected SecretKey toSecretKey(String key) {
        byte[] decodedKey = Base64.getDecoder().decode(key);
        return new SecretKeySpec(decodedKey, SignatureAlgorithm.HS512.getJcaName());
    }
}
