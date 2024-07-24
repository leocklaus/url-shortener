package io.github.leocklaus.url_sortener.domain.service;

import io.github.leocklaus.url_sortener.api.dto.URLInputDTO;
import io.github.leocklaus.url_sortener.builders.ShortenedURLBuilder;
import io.github.leocklaus.url_sortener.builders.URLInputDTOBuilder;
import io.github.leocklaus.url_sortener.builders.UserBuilder;
import io.github.leocklaus.url_sortener.domain.entity.ShortenedURL;
import io.github.leocklaus.url_sortener.domain.exception.NotAuthorizedException;
import io.github.leocklaus.url_sortener.domain.exception.URLNotFoundException;
import io.github.leocklaus.url_sortener.domain.exception.UserNotFoundException;
import io.github.leocklaus.url_sortener.domain.repository.URLRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class URLServiceTest {

    @Mock
    URLRepository urlRepository;

    @Mock
    UserService userService;

    @Mock
    StatsService statsService;

    @Captor
    ArgumentCaptor<String> urlCaptor;

    @Captor
    ArgumentCaptor<UUID> userIdCaptor;

    @Captor
    ArgumentCaptor<ShortenedURL> shortenedURLArgumentCaptor;

    @InjectMocks
    @Spy
    URLService urlService;

    @Nested
    class ShortenURL{
        @Test
        void shouldShortenerOriginalURL() {
            var originalURL = "http://www.google.com";
            var userId = UUID.fromString("19461ccd-27b7-4375-a949-da09c473db4e");

            var shortenedURLString = "abcde";

            var user = new UserBuilder()
                    .withId(userId)
                    .build();

            URLInputDTO inputDTO = new URLInputDTOBuilder()
                    .withOriginalURL(originalURL)
                    .withUserId(userId)
                    .build();

            ShortenedURL shortenedURL = new ShortenedURLBuilder()
                    .withOriginalURL(originalURL)
                    .withShortenedURL(shortenedURLString)
                    .withUser(user)
                    .build();

            doReturn(shortenedURLString).when(urlService).createShortenedURL(originalURL);
            doReturn(user).when(userService).getUserByUUIDOrThrowsExceptionIfNotExists(userIdCaptor.capture());
            doReturn(user).when(userService).getLoggedUserOrThrowsExceptionIfNotExists();
            doReturn(shortenedURL).when(urlRepository).save(shortenedURLArgumentCaptor.capture());

            var response = urlService.shortenURL(inputDTO);

            assertEquals(user.getId(), userIdCaptor.getValue());
            assertEquals(shortenedURL, shortenedURLArgumentCaptor.getValue());
            assertEquals(shortenedURL.getShortenedURL(), shortenedURLArgumentCaptor.getValue().getShortenedURL());
        }

        @Test
        void shouldThrowExceptionIfUserNotExists(){
            var originalURL = "http://www.google.com";
            var userId = UUID.fromString("19461ccd-27b7-4375-a949-da09c473db4e");

            var shortenedURLString = "abcde";

            URLInputDTO inputDTO = new URLInputDTOBuilder()
                    .withOriginalURL(originalURL)
                    .withUserId(userId)
                    .build();

            doReturn(shortenedURLString).when(urlService).createShortenedURL(originalURL);
            doThrow(UserNotFoundException.class).when(userService).getUserByUUIDOrThrowsExceptionIfNotExists(any());

            assertThrows(UserNotFoundException.class, ()->{
                urlService.shortenURL(inputDTO);
            });
        }

        @Test
        void shouldThrowExceptionIfNonUserIsLogged(){
            var originalURL = "http://www.google.com";
            var userId = UUID.fromString("19461ccd-27b7-4375-a949-da09c473db4e");

            var shortenedURLString = "abcde";

            URLInputDTO inputDTO = new URLInputDTOBuilder()
                    .withOriginalURL(originalURL)
                    .withUserId(userId)
                    .build();

            var user = new UserBuilder()
                    .withId(userId)
                    .build();

            doReturn(shortenedURLString).when(urlService).createShortenedURL(originalURL);
            doReturn(user).when(userService).getUserByUUIDOrThrowsExceptionIfNotExists(userIdCaptor.capture());
            doThrow(UserNotFoundException.class).when(userService).getLoggedUserOrThrowsExceptionIfNotExists();

            assertThrows(UserNotFoundException.class, ()->{
                urlService.shortenURL(inputDTO);
            });
        }

        @Test
        void shouldThrowExceptionIfUserAndLoggedUserAreNotTheSame(){
            var originalURL = "http://www.google.com";
            var userId = UUID.fromString("19461ccd-27b7-4375-a949-da09c473db4e");

            var shortenedURLString = "abcde";

            var user = new UserBuilder()
                    .withId(userId)
                    .build();

            var loggedUser = new UserBuilder()
                    .build();

            URLInputDTO inputDTO = new URLInputDTOBuilder()
                    .withOriginalURL(originalURL)
                    .withUserId(userId)
                    .build();

            ShortenedURL shortenedURL = new ShortenedURLBuilder()
                    .withOriginalURL(originalURL)
                    .withShortenedURL(shortenedURLString)
                    .withUser(user)
                    .build();

            doReturn(shortenedURLString).when(urlService).createShortenedURL(originalURL);
            doReturn(user).when(userService).getUserByUUIDOrThrowsExceptionIfNotExists(userIdCaptor.capture());
            doReturn(loggedUser).when(userService).getLoggedUserOrThrowsExceptionIfNotExists();

            assertThrows(NotAuthorizedException.class, ()->{
                    urlService.shortenURL(inputDTO);
            });


        }

    }

    @Nested
    class getOriginalURLByShortenedURLOrThrowsException{

        @Test
        @DisplayName("Should return original URL")
        void shouldReturnOriginalURL() {

            String url = "abcde";
            String originalURL = "http://www.google.com";
            var shortenURL = new ShortenedURLBuilder()
                    .withShortenedURL(url)
                    .withOriginalURL(originalURL)
                    .build();

            doReturn(shortenURL).when(urlService).findByShortenURLOrThrowsExceptionIfNotExists(url);

            var response = urlService.getOriginalURLByShortenedURLOrThrowsExceptionIfNotExists(url);

            assertEquals(originalURL, response);
            verify(urlService, times(1)).findByShortenURLOrThrowsExceptionIfNotExists(any());

        }

        @Test
        @DisplayName("Should throw exception if URL not found")
        void shouldThrowExceptionIfURLNotFound() {
            String url = "abcde";

            doThrow(URLNotFoundException.class).when(urlService)
                    .findByShortenURLOrThrowsExceptionIfNotExists(url);

            assertThrows(URLNotFoundException.class, ()->{
                urlService.getOriginalURLByShortenedURLOrThrowsExceptionIfNotExists(url);
            });
        }
    }

    @Nested
    class findByShortenURLOrThrowsException{
        @Test
        @DisplayName("Should return shortened URL entity")
        void shouldReturnShortenedURLEntity() {

            String url = "abcde";
            var shortenURL = new ShortenedURLBuilder()
                    .withShortenedURL(url)
                    .build();

            doReturn(Optional.of(shortenURL)).when(urlRepository).findByShortenedURL(urlCaptor.capture());

            var response = urlService.findByShortenURLOrThrowsExceptionIfNotExists(url);

            assertEquals(url, urlCaptor.getValue());
            assertEquals(response, shortenURL);
            verify(urlRepository, times(1)).findByShortenedURL(any());
        }

        @Test
        @DisplayName("Should throw exception if URL not found")
        void shouldThrowExceptionIfURLNotFound() {
            String url = "abcde";

            doReturn(Optional.empty()).when(urlRepository).findByShortenedURL(any());

            assertThrows(URLNotFoundException.class, ()->{
                urlService.findByShortenURLOrThrowsExceptionIfNotExists(url);
            });

        }
    }

    @Nested
    class createShortenedURL{
        @Test
        @DisplayName("Should return a new shortened url and a original url is inputted")
        void shouldReturnShortenedURL(){

            String originalURL = "http://www.google.com";

            doReturn(false).when(urlRepository).existsById(any());

            var response = urlService.createShortenedURL(originalURL);

            assertNotEquals(originalURL, response);
            assertTrue(response.length() >= 5);
            assertTrue(response.length() <=10);

        }
    }

}