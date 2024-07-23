package io.github.leocklaus.url_sortener.domain.exception;

import java.util.UUID;

public class UserNotFoundException extends UserException{
    public UserNotFoundException(String msg) {
        super(msg);
    }

    public UserNotFoundException(UUID id){
        this("User not found with id: " + id);
    }
}
