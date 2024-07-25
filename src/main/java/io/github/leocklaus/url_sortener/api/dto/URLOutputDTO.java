package io.github.leocklaus.url_sortener.api.dto;

import io.github.leocklaus.url_sortener.domain.entity.ShortenedURL;
import io.swagger.v3.oas.annotations.media.Schema;

public record URLOutputDTO(
        @Schema(example = "localhost:8080/rSYjjz", description = "Shortened URL") String shortenedURL,
        @Schema(example = "http://www.google.com", description = "Original URL")String originalURL) {

    public URLOutputDTO(ShortenedURL entity){
        this(entity.getShortenedURL(), entity.getOriginalURL());
    }

}
