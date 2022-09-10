package it.discovery.security.jwt;

@FunctionalInterface
public interface TokenValidator {

    /**
     * Validates authorization header and returns subject
     *
     * @param authHeader
     * @return
     */
    String validate(String authHeader);
}
