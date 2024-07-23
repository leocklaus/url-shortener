package io.github.leocklaus.url_sortener.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "tb_url")
public class ShortenedURL {

    @Id
    @EqualsAndHashCode.Include
    private String shortenedURL;

    @Column(nullable = false)
    private String originalURL;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Boolean isActive = true;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    private void setAsActive(){
        this.isActive = true;
    }

    private void setAsInactive(){
        this.isActive = false;
    }

}
