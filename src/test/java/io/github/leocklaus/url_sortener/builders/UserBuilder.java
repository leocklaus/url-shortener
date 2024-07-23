package io.github.leocklaus.url_sortener.builders;

import io.github.leocklaus.url_sortener.domain.entity.ShortenedURL;
import io.github.leocklaus.url_sortener.domain.entity.User;
import io.github.leocklaus.url_sortener.domain.entity.UserRoles;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserBuilder {

    private UUID id = UUID.randomUUID();
    private String name = "Random";
    private String email = "random@user.com";
    private String password = "123456";
    private List<ShortenedURL> urls = new ArrayList<>();
    private final Instant createdOn = Instant.now();
    private UserRoles roles = UserRoles.USER;

    public UserBuilder withId(UUID id){
        this.id = id;
        return this;
    }

    public UserBuilder withName(String name){
        this.name = name;
        return this;
    }

    public UserBuilder withEmail(String email){
        this.email = email;
        return this;
    }

    public UserBuilder withPassword(String password){
        this.password = password;
        return this;
    }

    public UserBuilder withUrl(List<ShortenedURL> shortenedURL){
        this.urls = shortenedURL;
        return this;
    }

    public UserBuilder asAdmin(){
        this.roles = UserRoles.ADMIN;
        return this;
    }

    public User build(){
        return new User(
                this.id,
                this.name,
                this.email,
                this.password,
                this.urls,
                this.createdOn,
                this.roles
        );
    }

}
