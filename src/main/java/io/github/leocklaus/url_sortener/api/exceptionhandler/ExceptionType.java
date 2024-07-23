package io.github.leocklaus.url_sortener.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ExceptionType {

    USER_NOT_FOUND("Usuário não encontrado", "/user-not-found"),
    URL_NOT_FOUND("URL não encontrada", "/url-not-found"),
    INVALID_DATA("Dados inválidos", "/invalid-data"),
    RESOURCE_NOT_FOUND("Recurso não encontrado", "/resource-not-found"),
    WRONG_PASSWORD("A senha digitada é incorreta", "/wrong-password"),
    NOT_AUTHORIZED("Requisição não autorizada ou credenciais inválidas", "/not-authorized"),
    EMAIL_ALREADY_TAKEN("O email já está em uso", "/email-already-taken");

    private String title;
    private String URI;

    ExceptionType(String title, String URI){
        this.title = title;
        this.URI = URI;
    }
}
