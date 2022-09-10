package it.discovery.security.config;

import it.discovery.security.jwt.JjwtTokenGenerator;
import it.discovery.security.jwt.JjwtTokenValidator;
import it.discovery.security.jwt.TokenGenerator;
import it.discovery.security.jwt.TokenValidator;
import it.discovery.security.jwt.nimbus.NimbusTokenGenerator;
import it.discovery.security.jwt.nimbus.NimbusTokenValidator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthConfig {

    @Bean
    @ConditionalOnProperty(name = "jwt.library", havingValue = "nimbus")
    TokenGenerator nimbusTokenGenerator(SecurityConfiguration securityConfig) {
        return new NimbusTokenGenerator(securityConfig);
    }

    @Bean
    @ConditionalOnProperty(name = "jwt.library", havingValue = "jjwt")
    TokenGenerator jjwtTokenGenerator(SecurityConfiguration securityConfig) {
        return new JjwtTokenGenerator(securityConfig);
    }

    @Bean
    @ConditionalOnProperty(name = "jwt.library", havingValue = "nimbus")
    TokenValidator nimbusTokenValidator(SecurityConfiguration securityConfig) {
        return new NimbusTokenValidator(securityConfig);
    }

    @Bean
    @ConditionalOnProperty(name = "jwt.library", havingValue = "jjwt")
    TokenValidator jjwtTokenValidator(SecurityConfiguration securityConfiguration) {
        return new JjwtTokenValidator(securityConfiguration);
    }
}
