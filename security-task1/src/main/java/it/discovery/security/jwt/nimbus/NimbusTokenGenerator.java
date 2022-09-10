package it.discovery.security.jwt.nimbus;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import it.discovery.security.config.SecurityConfiguration;
import it.discovery.security.jwt.TokenGenerator;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
public class NimbusTokenGenerator implements TokenGenerator {

    private final SecurityConfiguration securityConfig;

    @Override
    public String generate(String subject, LocalDateTime expirationTime) {
        byte[] bytes = Base64.getDecoder().decode(securityConfig.key());
        try {
            JWSSigner signer = new MACSigner(bytes);
            Date expirationDate = Date.from(expirationTime.atZone(ZoneId.systemDefault()).toInstant());
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(subject)
                    .expirationTime(expirationDate)
                    .build();
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS512), claimsSet);
            signedJWT.sign(signer);
            return signedJWT.serialize();

        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }
}
