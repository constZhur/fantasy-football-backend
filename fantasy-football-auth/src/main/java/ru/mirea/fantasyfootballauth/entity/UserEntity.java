package ru.mirea.fantasyfootballauth.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.UUID;

@Entity
@Table(name = "tournament_user", indexes = {
        @Index(name = "index_token", columnList = "token", unique = true)
})
@Data
public class UserEntity {

    @Id
    private UUID id;

    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "token")
    private String token;
}
