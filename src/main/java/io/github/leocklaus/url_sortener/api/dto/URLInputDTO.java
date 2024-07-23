package io.github.leocklaus.url_sortener.api.dto;

import java.util.UUID;

public record URLInputDTO(UUID userId, String originalURL) {
}
