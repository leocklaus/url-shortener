package io.github.leocklaus.url_sortener.api.dto;

import io.github.leocklaus.url_sortener.domain.entity.ShortenedURL;

public record URLOutputDTO(String shortenedURL, String originalURL) {

    public URLOutputDTO(ShortenedURL entity){
        this(entity.getShortenedURL(), entity.getOriginalURL());
    }

}
