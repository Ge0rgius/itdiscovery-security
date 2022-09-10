package it.discovery.security.key;

import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AESKeyGenerator implements KeyGenerator {
    @Override
    public String generate() {
        try {
            javax.crypto.KeyGenerator generator = javax.crypto.KeyGenerator.getInstance("AES");
            generator.init(256);
            SecretKey secretKey = generator.generateKey();
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        KeyGenerator generator = new AESKeyGenerator();
        System.out.println(generator.generate());
    }
}
