package it.discovery.security.jwt;

import java.time.LocalDateTime;

@FunctionalInterface
public interface TokenGenerator {

    String generate(String subject, LocalDateTime expirationTime);
}
