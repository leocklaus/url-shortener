package io.github.leocklaus.url_sortener.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserInputDTO(
        @NotNull @Size(min = 3) String name,
        @NotNull @Email String email,
        @NotNull @Size(min = 6) String password) {
}
