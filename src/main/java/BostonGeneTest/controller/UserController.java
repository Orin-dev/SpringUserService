package BostonGeneTest.controller;

import BostonGeneTest.dao.User;
import BostonGeneTest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Base64;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String index() {
        User user = new User();
        user.setId(1L);
        user.setName("jigurda");
        user.setEmail("123123");
        user.setSecondName("vasya");
        user.setPassword("qwerty");
        user.setBirthDate(LocalDate.now());

        userRepository.save(user);

        return "hello";
    }

    @GetMapping("/user")
    public User getUserByEmail(@RequestParam String email) {
        return userRepository.getUserByEmail(email);
    }

    @PostMapping(value = "/user",
            consumes = "application/json",
            produces = "application/json")
    public void addUser(@RequestBody User user) {
        user.setPassword(Base64.getEncoder().encodeToString(user.getPassword().getBytes()));
        userRepository.save(user);
    }

    @DeleteMapping(value = "/user")
    public void deleteUser(@RequestParam Long id) {
        userRepository.deleteById(id);
    }
}
