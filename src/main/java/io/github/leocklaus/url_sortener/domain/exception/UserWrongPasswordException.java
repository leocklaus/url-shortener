package io.github.leocklaus.url_sortener.domain.exception;

public class UserWrongPasswordException extends UserException{
    public UserWrongPasswordException(){
        super("A senha enviada está incorreta");
    }
}
