package ru.mirea.fantasyfootballengine.entity.jdbc;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "tournament_user")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    private UUID id;

    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
}


