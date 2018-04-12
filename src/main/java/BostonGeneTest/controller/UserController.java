package BostonGeneTest.controller;

import BostonGeneTest.EmailChecker;
import BostonGeneTest.dao.User;
import BostonGeneTest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    EmailChecker emailChecker;

    @GetMapping("/")
    public String index() {
        return "hello";
    }

    @GetMapping("/{email}")
    public User getUserByEmail(@RequestParam String email) {
        return userRepository.getUserByEmail(email);
    }

    @PostMapping(
            consumes = "application/json",
            produces = "application/json")
    public String addUser(@RequestBody User user) throws EmailAlreadyExistsException {

        if (emailChecker.check(user.getEmail())) {

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);

            return "ok";

        } else
            throw new EmailAlreadyExistsException("Email already exists: Email=" + user.getEmail());

    }

    @DeleteMapping(value = "/user")
    public void deleteUser(@RequestParam Long id) {
        userRepository.deleteById(id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public String returnBadRequest(EmailAlreadyExistsException ex) {
        return ex.getMessage();
    }
}
