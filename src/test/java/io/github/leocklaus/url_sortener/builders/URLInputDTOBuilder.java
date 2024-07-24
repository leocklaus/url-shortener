package io.github.leocklaus.url_sortener.builders;

import io.github.leocklaus.url_sortener.api.dto.URLInputDTO;

import java.util.UUID;

public class URLInputDTOBuilder {
    private UUID userId = UUID.randomUUID();
    private String originalURL = "http://www.google.com";

    public URLInputDTOBuilder withUserId(UUID id){
        this.userId = id;
        return this;
    }

    public URLInputDTOBuilder withOriginalURL(String originalURL){
        this.originalURL = originalURL;
        return this;
    }

    public URLInputDTO build(){
        return new URLInputDTO(
                this.userId,
                this.originalURL
        );
    }
}
