package io.github.leocklaus.url_sortener.domain.service;

import io.github.leocklaus.url_sortener.api.dto.URLInputDTO;
import io.github.leocklaus.url_sortener.api.dto.URLOutputDTO;
import io.github.leocklaus.url_sortener.domain.entity.ShortenedURL;
import io.github.leocklaus.url_sortener.domain.entity.User;
import io.github.leocklaus.url_sortener.domain.exception.URLNotFoundException;
import io.github.leocklaus.url_sortener.domain.repository.URLRepository;
import io.github.leocklaus.url_sortener.domain.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class URLService {

    @Autowired
    private URLRepository urlRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private StatsService statsService;


    public URLOutputDTO shortenURL(URLInputDTO dto) {

        var shortenedURL = createShortenedURL(dto.originalURL());

        var user = userService.getUserByUUIDOrThrowsExceptionIfNotExists(dto.userId());

        var entity = ShortenedURL.builder()
                .shortenedURL(shortenedURL)
                .originalURL(dto.originalURL())
                .user(user)
                .build();

        entity = urlRepository.save(entity);

        return new URLOutputDTO(entity);

    }

    public String getOriginalURLByShortenedURLOrThrowsExceptionIfNotExists(String shortenedURL){

        ShortenedURL url = findByShortenURLOrThrowsExceptionIfNotExists(shortenedURL);

        statsService.addViewCountToURL(url);

        return url.getOriginalURL();
    }

    private String createShortenedURL(String originalURL) {

        String url;

        do{
            url = RandomStringUtils.randomAlphanumeric(5, 10);
        }while (urlRepository.existsById(url));

        return url;
    }

    public ShortenedURL findByShortenURLOrThrowsExceptionIfNotExists(String url){
        return urlRepository.findByShortenedURL(url)
                .orElseThrow(()-> new URLNotFoundException(url));

    }
}
