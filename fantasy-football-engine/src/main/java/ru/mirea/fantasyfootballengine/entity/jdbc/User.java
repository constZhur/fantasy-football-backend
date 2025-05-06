package ru.mirea.fantasyfootballengine.entity.jdbc;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "tournament_user")
public class User {
    @Id
    private UUID id;

    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
}


