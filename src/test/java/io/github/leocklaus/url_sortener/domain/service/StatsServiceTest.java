package io.github.leocklaus.url_sortener.domain.service;

import io.github.leocklaus.url_sortener.builders.ShortenedURLBuilder;
import io.github.leocklaus.url_sortener.builders.StatsBuilder;
import io.github.leocklaus.url_sortener.domain.entity.ShortenedURL;
import io.github.leocklaus.url_sortener.domain.entity.Stats;
import io.github.leocklaus.url_sortener.domain.exception.URLNotFoundException;
import io.github.leocklaus.url_sortener.domain.repository.StatsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatsServiceTest {

    @Mock
    StatsRepository statsRepository;

    @Mock
    URLService urlService;

    @Captor
    ArgumentCaptor<Stats> statsArgumentCaptor;

    @Captor
    ArgumentCaptor<ShortenedURL> shortenedURLArgumentCaptor;

    @Captor
    ArgumentCaptor<String> urlStringCaptor;

    @InjectMocks
    StatsService statsService;

    @Nested
    class addViewCountToURL{

        @Test
        @DisplayName("Should Create a Stats entity correctly from URL String")
        void shouldCreateAStatsEntityCorrectlyFromURL() {

            var url = new ShortenedURLBuilder()
                    .build();

            var stats = new StatsBuilder()
                    .withURL(url)
                    .build();

            doReturn(stats).when(statsRepository).save(statsArgumentCaptor.capture());

            statsService.addViewCountToURL(url);

            assertEquals(stats.getUrlID(), statsArgumentCaptor.getValue().getUrlID());

        }

        @Test
        @DisplayName("Should Persist a Stats entity correctly")
        void shouldPersistStatsEntityCorrectly() {

            var url = new ShortenedURLBuilder()
                    .build();

            var stats = new StatsBuilder()
                    .withURL(url)
                    .build();

            doReturn(stats).when(statsRepository).save(any());

            statsService.addViewCountToURL(url);

            verify(statsRepository, times(1)).save(any());

        }
    }

    @Nested
    class getTotalViewsByURL{
        @Test
        @DisplayName("Should Throw Exception if URL Not Exists")
        void shouldThrowExceptionIfURLNotExists() {

            String url = "http://www.google.com";

            doThrow(URLNotFoundException.class).when(urlService)
                    .findByShortenURLOrThrowsExceptionIfNotExists(any());

            assertThrows(URLNotFoundException.class, ()->{
                statsService.getTotalViewsByURL(url);
            });


        }

        @Test
        @DisplayName("Should return total views by URL")
        void shouldReturnTotalViewsByURL(){

            String url = "http://www.google.com";
            var shortenedURL = new ShortenedURLBuilder()
                    .withShortenedURL(url)
                    .build();

            doReturn(shortenedURL).when(urlService)
                    .findByShortenURLOrThrowsExceptionIfNotExists(urlStringCaptor.capture());

            doReturn(2L).when(statsRepository)
                    .countByUrlID(shortenedURLArgumentCaptor.capture());

            var response = statsService.getTotalViewsByURL(url);

            assertEquals(2L, response.count());
            assertEquals(url, urlStringCaptor.getValue());
            assertEquals(shortenedURL.getShortenedURL(), shortenedURLArgumentCaptor.getValue().getShortenedURL());

        }
    }

}