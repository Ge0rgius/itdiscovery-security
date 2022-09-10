package it.discovery.security.jwt.nimbus;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.DirectDecrypter;
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
            //TODO make encryption optional
            JWEObject jweObject = JWEObject.parse(token);
            jweObject.decrypt(new DirectDecrypter(toSecretKey(securityConfig.encryptionKey())));

            JWSVerifier verifier = new MACVerifier(toSecretKey(securityConfig.signKey()));
            SignedJWT signedJWT = jweObject.getPayload().toSignedJWT();

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
