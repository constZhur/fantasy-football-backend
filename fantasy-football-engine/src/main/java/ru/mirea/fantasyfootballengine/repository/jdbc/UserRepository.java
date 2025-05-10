package ru.mirea.fantasyfootballengine.repository.jdbc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.fantasyfootballengine.entity.jdbc.User;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

}
