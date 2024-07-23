package io.github.leocklaus.url_sortener.domain.repository;

import io.github.leocklaus.url_sortener.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
