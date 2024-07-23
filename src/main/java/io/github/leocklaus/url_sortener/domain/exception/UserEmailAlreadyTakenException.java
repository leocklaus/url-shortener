package io.github.leocklaus.url_sortener.domain.exception;

public class UserEmailAlreadyTakenException extends UserException{
    public UserEmailAlreadyTakenException(String email) {
        super(email + "is already taken.");
    }

}
