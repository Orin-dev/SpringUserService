package ru.orindev.BostonGeneTest.SpringUserService.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import ru.orindev.BostonGeneTest.SpringUserService.model.User;
import ru.orindev.BostonGeneTest.SpringUserService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public @ResponseBody
    ResponseEntity<User> getUserByEmail(@RequestParam String email) throws UserNotFoundException {
        User user = userRepository.getUserByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("пользователя с таким Email нет");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(user, headers, HttpStatus.OK);
    }

    @PostMapping
    public @ResponseBody
    ResponseEntity<User> addUser(@RequestBody User user) throws EmailAlreadyExistsException {

        if (emailChecker.check(user.getEmail())) {

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User repositoryResponse = userRepository.save(user);
            if (repositoryResponse != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "application/json; charset=UTF-8");
                ResponseEntity<User> responseEntity = new ResponseEntity<>(repositoryResponse, headers, HttpStatus.CREATED);
                return responseEntity;
            } else
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        } else
            throw new EmailAlreadyExistsException("Email already exists: Email=" + user.getEmail());

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteUser(@PathVariable int id) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=UTF-8");
        try {
            userRepository.deleteById(Long.valueOf(id));
            return new ResponseEntity(headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(headers, HttpStatus.BAD_REQUEST);
        }
    }

}
