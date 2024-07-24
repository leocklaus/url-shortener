package io.github.leocklaus.url_sortener.builders;

import io.github.leocklaus.url_sortener.api.dto.URLOutputDTO;

public class URLOutputDTOBuilder {

    private String shortenedURL = "abcde";
    private String originalURL = "http://www.google.com";

    public URLOutputDTOBuilder withShortenedURL(String url){
        this.shortenedURL = url;
        return this;
    }

    public URLOutputDTOBuilder withOriginalURL(String originalURL){
        this.originalURL = originalURL;
        return this;
    }

    public URLOutputDTO build(){
        return new URLOutputDTO(
                this.shortenedURL,
                this.originalURL
        );
    }

}
