package io.github.leocklaus.url_sortener.builders;

import io.github.leocklaus.url_sortener.domain.entity.ShortenedURL;
import io.github.leocklaus.url_sortener.domain.entity.Stats;

import java.time.Instant;

public class StatsBuilder {

    private Long id = 1L;
    private ShortenedURL url = new ShortenedURL();
    private final Instant createdAt = Instant.now();

    public StatsBuilder withId(Long id){
        this.id = id;
        return this;
    }

    public StatsBuilder withURL(ShortenedURL url){
        this.url = url;
        return this;
    }

    public Stats build(){
        return new Stats(
                id,
                url,
                createdAt
        );
    }
}
