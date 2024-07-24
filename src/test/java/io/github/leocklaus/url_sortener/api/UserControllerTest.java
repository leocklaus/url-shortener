package io.github.leocklaus.url_sortener.api;

import io.github.leocklaus.url_sortener.api.dto.UserInputDTO;
import io.github.leocklaus.url_sortener.builders.UserBuilder;
import io.github.leocklaus.url_sortener.builders.UserInputBuilder;
import io.github.leocklaus.url_sortener.config.security.TokenService;
import io.github.leocklaus.url_sortener.domain.service.UserService;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;

import java.net.URI;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    UserService userService;

    @Mock
    TokenService tokenService;

    @Mock
    AuthenticationManager authenticationManager;

    @InjectMocks
    UserController userController;

    @Nested
    class AddUser{
        @Test
        void addUserShouldReturnStatus201() {
            var token = "12345678";
            var name = "nome";
            var email = "email@email.com";
            var password = "123456";

            var userDTO = new UserInputBuilder()
                    .withName(name)
                    .withEmail(email)
                    .withPassword(password)
                    .build();

            var user = new UserBuilder()
                    .withName(name)
                    .withEmail(email)
                    .withPassword(password)
                    .build();

            URI location = URI.create("/user/" + user.getId());

            doReturn(user).when(userService).addNewUser(userDTO);
            doReturn(token).when(tokenService).generateToken(user);

            var response = userController.addUser(userDTO);

            assertEquals(HttpStatus.CREATED,response.getStatusCode());
            assertEquals(location, response.getHeaders().getLocation());
        }

        @Test
        void addUserShouldReturnCorrectDTOWithToken(){

            var token = "12345678";
            var name = "nome";
            var email = "email@email.com";
            var password = "123456";

            var userDTO = new UserInputBuilder()
                    .withName(name)
                    .withEmail(email)
                    .withPassword(password)
                    .build();

            var user = new UserBuilder()
                    .withName(name)
                    .withEmail(email)
                    .withPassword(password)
                    .build();

            doReturn(user).when(userService).addNewUser(userDTO);
            doReturn(token).when(tokenService).generateToken(user);

            var response = userController.addUser(userDTO);

            assertTrue(response.hasBody());
            assertEquals(user.getId(), Objects.requireNonNull(response.getBody()).userId());
            assertEquals(token, response.getBody().token());

        }
    }


}