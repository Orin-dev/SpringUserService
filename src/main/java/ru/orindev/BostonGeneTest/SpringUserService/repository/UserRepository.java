package ru.orindev.BostonGeneTest.SpringUserService.repository;

import org.springframework.stereotype.Repository;
import ru.orindev.BostonGeneTest.SpringUserService.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getUserByEmail(String email);
}
