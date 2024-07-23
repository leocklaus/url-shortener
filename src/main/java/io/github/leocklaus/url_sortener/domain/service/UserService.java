package io.github.leocklaus.url_sortener.domain.service;

import io.github.leocklaus.url_sortener.api.dto.UserInputDTO;
import io.github.leocklaus.url_sortener.domain.entity.User;
import io.github.leocklaus.url_sortener.domain.exception.UserNotFoundException;
import io.github.leocklaus.url_sortener.domain.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByUUIDOrThrowsExceptionIfNotExists(UUID uuid){
        return userRepository.findById(uuid)
                .orElseThrow(()-> new UserNotFoundException(uuid));
    }

    public User addNewUser(UserInputDTO dto) {

        User user = User.builder()
                .name(dto.name())
                .email(dto.email())
                .password(encodePassword(dto.password()))
                .build();

        return userRepository.save(user);
    }

    private String encodePassword(String password){
        //TO BE IMPLEMENTED
        return password;
    }
}
