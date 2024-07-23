package io.github.leocklaus.url_sortener.api.dto;

import jakarta.validation.constraints.NotNull;

public record AuthDTO(@NotNull String login, @NotNull String password) {
}
