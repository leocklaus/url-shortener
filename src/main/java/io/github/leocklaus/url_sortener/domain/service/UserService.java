package io.github.leocklaus.url_sortener.domain.service;

import io.github.leocklaus.url_sortener.api.dto.UserInputDTO;
import io.github.leocklaus.url_sortener.domain.entity.User;
import io.github.leocklaus.url_sortener.domain.exception.UserEmailAlreadyTakenException;
import io.github.leocklaus.url_sortener.domain.exception.UserNotFoundException;
import io.github.leocklaus.url_sortener.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthorizationService authorizationService;

    public UserService(UserRepository userRepository, AuthorizationService authorizationService) {
        this.userRepository = userRepository;
        this.authorizationService = authorizationService;
    }

    public User getUserByUUIDOrThrowsExceptionIfNotExists(UUID uuid){
        return userRepository.findById(uuid)
                .orElseThrow(()-> new UserNotFoundException(uuid));
    }

    @Transactional
    public User addNewUser(UserInputDTO dto) {

        if(userRepository.existsByEmail(dto.email())){
            throw new UserEmailAlreadyTakenException(dto.email());
        }

        User user = User.builder()
                .name(dto.name())
                .email(dto.email())
                .password(encodePassword(dto.password()))
                .build();

        return userRepository.save(user);
    }

    private String encodePassword(String password){
        return new BCryptPasswordEncoder().encode(password);
    }

    public User getLoggedUserOrThrowsExceptionIfNotExists(){
        String authenticatedUserEmail = authorizationService.getAuthenticatedUsername();
        Optional<User> user = userRepository.findByEmail(authenticatedUserEmail);

        if(user.isEmpty()){
            throw new UserNotFoundException("User not found with email: " + authenticatedUserEmail);
        }

        return user.get();
    }
}
