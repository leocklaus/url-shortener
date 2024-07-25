package io.github.leocklaus.url_sortener.domain.service;

import io.github.leocklaus.url_sortener.api.dto.URLCountOutputDTO;
import io.github.leocklaus.url_sortener.domain.entity.ShortenedURL;
import io.github.leocklaus.url_sortener.domain.entity.Stats;
import io.github.leocklaus.url_sortener.domain.entity.User;
import io.github.leocklaus.url_sortener.domain.exception.NotAuthorizedException;
import io.github.leocklaus.url_sortener.domain.repository.StatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

    @Autowired
    private StatsRepository statsRepository;
    @Autowired
    private UserService userService;
    @Autowired
    @Lazy
    private URLService urlService;


    public void addViewCountToURL(ShortenedURL shortenedURL){

        Stats stat = Stats.builder()
                .urlID(shortenedURL)
                .build();

        statsRepository.save(stat);
    }

    public URLCountOutputDTO getTotalViewsByURL(String url){
        User loggedUser = userService.getLoggedUserOrThrowsExceptionIfNotExists();
        var shortenedURL = urlService.findByShortenURLOrThrowsExceptionIfNotExists(url);

        if(shortenedURL.getUser() != loggedUser){
            throw new NotAuthorizedException();
        }

        long viewsCount = statsRepository.countByUrlID(shortenedURL);
        return new URLCountOutputDTO(shortenedURL.getShortenedURL(), viewsCount);
    }
}
