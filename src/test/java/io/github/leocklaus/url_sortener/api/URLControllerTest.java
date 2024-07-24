package io.github.leocklaus.url_sortener.api;

import io.github.leocklaus.url_sortener.api.dto.URLInputDTO;
import io.github.leocklaus.url_sortener.api.dto.URLOutputDTO;
import io.github.leocklaus.url_sortener.builders.URLInputDTOBuilder;
import io.github.leocklaus.url_sortener.builders.URLOutputDTOBuilder;
import io.github.leocklaus.url_sortener.config.security.TokenService;
import io.github.leocklaus.url_sortener.domain.exception.URLNotFoundException;
import io.github.leocklaus.url_sortener.domain.service.StatsService;
import io.github.leocklaus.url_sortener.domain.service.URLService;
import io.github.leocklaus.url_sortener.domain.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class URLControllerTest {

    @Mock
    URLService urlService;

    @Mock
    StatsService statsService;

    @InjectMocks
    URLController urlController;

    @Nested
    class shortenURL{
        @Test
        @DisplayName("Should return status created and Location Header")
        void shouldReturnStatusCreatedAndLocationHeader() {

            URLInputDTO urlDTO = new URLInputDTOBuilder()
                    .build();

            URLOutputDTO urlOutput = new URLOutputDTOBuilder()
                    .build();

            URI uri = URI.create("/" + urlOutput.shortenedURL());

            doReturn(urlOutput).when(urlService).shortenURL(urlDTO);

            var response = urlController.shortenURL(urlDTO);

            assertEquals(HttpStatus.CREATED, response.getStatusCode());
            assertEquals(uri, response.getHeaders().getLocation());

        }

        @Test
        @DisplayName("Should return correct DTO")
        void shouldReturnCorrectDTO() {
            URLInputDTO urlDTO = new URLInputDTOBuilder()
                    .build();

            URLOutputDTO urlOutput = new URLOutputDTOBuilder()
                    .build();

            URI uri = URI.create("/" + urlOutput.shortenedURL());

            doReturn(urlOutput).when(urlService).shortenURL(urlDTO);

            var response = urlController.shortenURL(urlDTO);

            assertEquals(urlOutput.shortenedURL(), Objects.requireNonNull(response.getBody()).shortenedURL());
            assertEquals(urlOutput.originalURL(), Objects.requireNonNull(response.getBody()).originalURL());
        }
    }

    @Nested
    class redirect{
        @Test
        @DisplayName("Should return status found and correct header location")
        void shouldReturnStatusFoundAndCorrectHeaderLocation() {

            String url = "abcde";
            String originalURL = "http://www.google.com";

            doReturn(originalURL).when(urlService)
                    .getOriginalURLByShortenedURLOrThrowsExceptionIfNotExists(url);

            URI uri = URI.create(originalURL);

            var response = urlController.redirect(url);

            assertEquals(HttpStatus.FOUND, response.getStatusCode());
            assertEquals(uri, response.getHeaders().getLocation());

        }

        @Test
        @DisplayName("Should Throw Exception if original url do not exists")
        void shouldThrowExceptionIfOriginalURLDoNotExists(){
            String url = "abcde";

            doThrow(URLNotFoundException.class).when(urlService)
                    .getOriginalURLByShortenedURLOrThrowsExceptionIfNotExists(url);

            assertThrows(URLNotFoundException.class, ()->{
                urlController.redirect(url);
            });
        }
    }

}