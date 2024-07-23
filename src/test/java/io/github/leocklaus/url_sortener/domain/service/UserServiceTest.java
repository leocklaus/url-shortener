package io.github.leocklaus.url_sortener.domain.service;

import io.github.leocklaus.url_sortener.builders.UserBuilder;
import io.github.leocklaus.url_sortener.builders.UserDTOBuilder;
import io.github.leocklaus.url_sortener.domain.entity.User;
import io.github.leocklaus.url_sortener.domain.exception.UserEmailAlreadyTakenException;
import io.github.leocklaus.url_sortener.domain.exception.UserNotFoundException;
import io.github.leocklaus.url_sortener.domain.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    AuthorizationService authorizationService;

    @InjectMocks
    UserService userService;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor
    ArgumentCaptor<User> userArgumentCaptor;

    @Nested
    class GetUserByUUID{

        @Test
        @DisplayName("Should return User if UUID exists")
        void shouldReturnUserIfUUIDExists() {

            var uuid = UUID.randomUUID();
            var user = new UserBuilder()
                    .withId(uuid)
                    .build();

            doReturn(Optional.of(user)).when(userRepository).findById(uuidArgumentCaptor.capture());

            var response = userService.getUserByUUIDOrThrowsExceptionIfNotExists(uuid);

            assertEquals(user, response);
            assertEquals(uuid, uuidArgumentCaptor.getValue());
            verify(userRepository, times(1)).findById(any());

        }

        @Test
        @DisplayName("Should Throw UserNotFoundException if user not exists")
        void shouldThrowUserNotFoundExceptionIfUserNotExists() {
            var uuid = UUID.randomUUID();

            doReturn(Optional.empty()).when(userRepository).findById(any());

            assertThrows(UserNotFoundException.class, ()-> {
                userService.getUserByUUIDOrThrowsExceptionIfNotExists(uuid);
            });
        }

    }

    @Nested
    class addNewUser{

        @Test
        @DisplayName("Should build new user entity correctly from DTO")
        void shouldBuildUserEntityCorrectlyFromDTO() {
            var name = "user";
            var email = "user@email.com";
            var password = "123456";

            var dto = new UserDTOBuilder()
                    .withName(name)
                    .withEmail(email)
                    .withPassword(password)
                    .build();

            var expectedArgumentUser = new UserBuilder()
                    .withId(null)
                    .withName(name)
                    .withEmail(email)
                    .withPassword(password)
                    .build();

            var user = new UserBuilder()
                    .withName(name)
                    .withEmail(email)
                    .withPassword(password)
                    .build();

            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());

            userService.addNewUser(dto);

            assertEquals(expectedArgumentUser.getId(), userArgumentCaptor.getValue().getId());
            assertEquals(expectedArgumentUser.getName(), userArgumentCaptor.getValue().getName());
            assertEquals(expectedArgumentUser.getEmail(), userArgumentCaptor.getValue().getEmail());
            assertNotEquals(expectedArgumentUser.getPassword(), userArgumentCaptor.getValue().getPassword());
        }

        @Test
        @DisplayName("Should encode password correctly")
        void shouldEncodePasswordCorrectly() {

            var name = "user";
            var email = "user@email.com";
            var password = "123456";

            var dto = new UserDTOBuilder()
                    .withName(name)
                    .withEmail(email)
                    .withPassword(password)
                    .build();

            var user = new UserBuilder()
                    .withName(name)
                    .withEmail(email)
                    .withPassword(password)
                    .build();

            doReturn(user).when(userRepository).save(userArgumentCaptor.capture());

            userService.addNewUser(dto);

            assertTrue(new BCryptPasswordEncoder().matches(
                    password, userArgumentCaptor.getValue().getPassword())
            );
        }

        @Test
        @DisplayName("Should return a new user")
        void shouldReturnANewUser(){

            var name = "user";
            var email = "user@email.com";
            var password = "123456";

            var dto = new UserDTOBuilder()
                    .withName(name)
                    .withEmail(email)
                    .withPassword(password)
                    .build();

            var user = new UserBuilder()
                    .withName(name)
                    .withEmail(email)
                    .withPassword(password)
                    .build();

            doReturn(user).when(userRepository).save(any());

            var response = userService.addNewUser(dto);

            assertEquals(response.getId(), user.getId());
            assertEquals(response.getName(), user.getName());
            assertEquals(response.getPassword(), user.getPassword());
        }

        @Test
        @DisplayName("Should Throw EmailAlreadyTaken exception if email already exists")
        void shouldThrowExceptionIfEmailAlreadyTaken() {

            var email = "user@email.com";

            var dto = new UserDTOBuilder()
                    .withEmail(email)
                    .build();

            doReturn(true).when(userRepository).existsByEmail(email);

            assertThrows(UserEmailAlreadyTakenException.class, ()-> {
                userService.addNewUser(dto);
            });

        }

    }

    @Nested
    class GetLoggedUserOrThrowsException{

        @Test
        @DisplayName("Should return Logged User if user found by email")
        void shouldReturnLoggedUserIfUserFound() {

            String email = "email@test.com";
            var user = new UserBuilder()
                        .withEmail(email)
                        .build();

            doReturn(email).when(authorizationService).getAuthenticatedUsername();
            doReturn(Optional.of(user)).when(userRepository).findByEmail(email);

            var response = userService.getLoggedUserOrThrowsExceptionIfNotExists();

            assertEquals(user, response);
            assertEquals(user.getEmail(), response.getEmail());
            verify(authorizationService, times(1)).getAuthenticatedUsername();
            verify(userRepository, times(1)).findByEmail(any());

        }

        @Test
        @DisplayName("Should Throw UserNotFoundException if logged user not found")
        void shouldThrowUserNotFoundExceptionIfLoggedUserNotFound() {

            String email = "email@test.com";
            var user = new UserBuilder()
                    .withEmail(email)
                    .build();

            doReturn(email).when(authorizationService).getAuthenticatedUsername();
            doReturn(Optional.empty()).when(userRepository).findByEmail(email);

            assertThrows(UserNotFoundException.class, ()->{
                userService.getLoggedUserOrThrowsExceptionIfNotExists();
            });

        }

    }


}