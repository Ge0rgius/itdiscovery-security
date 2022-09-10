package it.discovery.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("security")
public record SecurityConfiguration(String key) {
}
