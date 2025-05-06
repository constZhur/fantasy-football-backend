package ru.mirea.fantasyfootballengine.repository.jdbc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.fantasyfootballengine.entity.jdbc.Footballer;

import java.util.UUID;

@Repository
public interface FootballerRepository extends JpaRepository<Footballer, UUID> {
}

