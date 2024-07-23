package io.github.leocklaus.url_sortener.api;

import io.github.leocklaus.url_sortener.api.dto.AuthDTO;
import io.github.leocklaus.url_sortener.api.dto.LoginResponseDTO;
import io.github.leocklaus.url_sortener.api.dto.UserInputDTO;
import io.github.leocklaus.url_sortener.api.dto.UserOutputDTO;
import io.github.leocklaus.url_sortener.config.security.TokenService;
import io.github.leocklaus.url_sortener.domain.entity.User;
import io.github.leocklaus.url_sortener.domain.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

@Controller
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public UserController(UserService userService, TokenService tokenService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody @Valid UserInputDTO dto){

        User user = userService.addNewUser(dto);
        URI uri = URI.create("/user/" + user.getId());

        var token = tokenService.generateToken(user);

        return ResponseEntity.created(uri).body(new UserOutputDTO(user.getId(), token));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthDTO authDTO){
        var usernamePassword = new UsernamePasswordAuthenticationToken(authDTO.login(), authDTO.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token, ((User) auth.getPrincipal()).getUsername()));
    }

}
