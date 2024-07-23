package io.github.leocklaus.url_sortener.domain.exception;

public class NotAuthorizedException extends RuntimeException{
    public NotAuthorizedException(){
        super("Requisição não autorizada ou com credenciais inválidas");
    }
}
