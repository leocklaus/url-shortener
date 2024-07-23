package io.github.leocklaus.url_sortener.domain.repository;

import io.github.leocklaus.url_sortener.domain.entity.ShortenedURL;
import io.github.leocklaus.url_sortener.domain.entity.Stats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatsRepository extends JpaRepository<Stats, Long> {
    long countByUrlID(ShortenedURL shortenedURL);
}
