package io.github.leocklaus.url_sortener.builders;

import io.github.leocklaus.url_sortener.domain.entity.ShortenedURL;
import io.github.leocklaus.url_sortener.domain.entity.User;

import java.time.Instant;

public class ShortenedURLBuilder {

    private String shortenedURL = "abcde";
    private String originalURL = "http://www.google.com";
    private User user = new UserBuilder().build();
    private Boolean isActive = true;
    private final Instant createdAt = Instant.now();
    private final Instant updatedAt = Instant.now();

    public ShortenedURLBuilder withShortenedURL(String shortenURL){
        this.shortenedURL = shortenURL;
        return this;
    }

    public ShortenedURLBuilder withOriginalURL(String originalURL){
        this.originalURL = originalURL;
        return this;
    }

    public ShortenedURLBuilder withUser(User user){
        this.user = user;
        return this;
    }

    public ShortenedURLBuilder asInactive(){
        this.isActive = false;
        return this;
    }

    public ShortenedURL build(){
        return new ShortenedURL(
                shortenedURL,
                originalURL,
                user,
                isActive,
                createdAt,
                updatedAt
        );
    }

}
