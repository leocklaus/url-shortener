package io.github.leocklaus.url_sortener.api.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record URLInputDTO(@NotNull UUID userId, @NotNull String originalURL) {
}
