package ru.rsreu.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rsreu.app.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

    @Override
    void delete(User user);

    Optional<User> findByUsername(String username);
}
