package it.discovery.security.key;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Base64;

public class Hs512KeyGenerator implements KeyGenerator {
    @Override
    public String generate() {
        SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public static void main(String[] args) {
        KeyGenerator keyGenerator = new Hs512KeyGenerator();
        System.out.println(keyGenerator.generate());
    }
}
