package ru.orindev.BostonGeneTest.SpringUserService.repository;

import ru.orindev.BostonGeneTest.SpringUserService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    User getUserByEmail(String email);
}
