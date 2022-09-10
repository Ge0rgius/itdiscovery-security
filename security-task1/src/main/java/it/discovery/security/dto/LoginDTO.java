package it.discovery.security.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginDTO(@NotBlank String username) {
}
