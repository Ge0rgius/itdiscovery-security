package it.discovery.security.jwt.nimbus;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import it.discovery.security.config.SecurityConfiguration;
import it.discovery.security.jwt.BaseTokenValidator;
import it.discovery.security.jwt.TokenValidator;
import lombok.RequiredArgsConstructor;

import java.text.ParseException;

@RequiredArgsConstructor
public class NimbusTokenValidator extends BaseTokenValidator implements TokenValidator {

    private final SecurityConfiguration securityConfig;

    @Override
    public String validate(String authHeader) {

        String token = extractToken(authHeader);
        try {
            JWSVerifier verifier = new MACVerifier(toSecretKey(securityConfig.key()));
            SignedJWT signedJWT = SignedJWT.parse(token);

            boolean success = signedJWT.verify(verifier);
            if (!success) {
                throw new JOSEException("JWS validation failed");
            }
            return signedJWT.getJWTClaimsSet().getSubject();
        } catch (JOSEException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
