package ru.orindev.BostonGeneTest.repository;

import ru.orindev.BostonGeneTest.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    User getUserByEmail(String email);
}
