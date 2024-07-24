package io.github.leocklaus.url_sortener.builders;

import io.github.leocklaus.url_sortener.api.dto.UserInputDTO;

public class UserDTOBuilder {
    private String name = "some";
    private String email = "some@person.com";
    private String password = "123456";

    public UserDTOBuilder withName(String name){
        this.name = name;
        return this;
    }

    public UserDTOBuilder withEmail(String email){
        this.email = email;
        return this;
    }

    public UserDTOBuilder withPassword(String password){
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
