package BostonGeneTest.repository;

import BostonGeneTest.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
    User getUserByEmail(String email);
}
