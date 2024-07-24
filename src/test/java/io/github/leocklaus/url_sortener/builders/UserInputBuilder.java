package io.github.leocklaus.url_sortener.builders;

import io.github.leocklaus.url_sortener.api.dto.UserInputDTO;

public class UserInputBuilder {
    private String name = "user";
    private String email = "user@user.com";
    private String password = "123456";

    public UserInputBuilder withName(String name){
        this.name = name;
        return this;
    }

    public UserInputBuilder withEmail(String email){
        this.email = email;
        return this;
    }

    public UserInputBuilder withPassword(String password){
        this.password = password;
        return this;
    }

    public UserInputDTO build(){
        return new UserInputDTO(
                this.name,
                this.email,
                this.password
        );
    }
}
