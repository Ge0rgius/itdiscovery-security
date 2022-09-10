package it.discovery.security.config;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties("security")
@Validated
public record SecurityConfiguration(@NotBlank String signKey, @NotBlank String encryptionKey,
                                    boolean encryptionEnabled) {
}
