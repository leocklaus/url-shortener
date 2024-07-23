package io.github.leocklaus.url_sortener.api;

import io.github.leocklaus.url_sortener.api.dto.UserInputDTO;
import io.github.leocklaus.url_sortener.domain.entity.User;
import io.github.leocklaus.url_sortener.domain.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody UserInputDTO dto){

        User user = userService.addNewUser(dto);
        URI uri = URI.create("/user/" + user.getId());

        return ResponseEntity.created(uri).build();
    }

}
