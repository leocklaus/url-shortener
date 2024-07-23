package io.github.leocklaus.url_sortener.domain.repository;

import io.github.leocklaus.url_sortener.domain.entity.ShortenedURL;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface URLRepository extends JpaRepository<ShortenedURL, String> {

    Optional<ShortenedURL> findByShortenedURL(String shortenedURL);

}
