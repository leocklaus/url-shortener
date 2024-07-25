package io.github.leocklaus.url_sortener.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record URLInputDTO(
        @Schema(description = "User ID") @NotNull UUID userId,
        @Schema(example = "http://www.google.com", description = "Original URL") @NotNull String originalURL) {
}
