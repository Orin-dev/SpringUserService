package ru.orindev.BostonGeneTest.SpringUserService.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import ru.orindev.BostonGeneTest.SpringUserService.EmailChecker;
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

    @GetMapping("")
    public @ResponseBody
    User getUserByEmail(@RequestParam String email) {
        User user = userRepository.getUserByEmail(email);
        return user;
    }

    @PostMapping(
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<User> addUser(@RequestBody User user) throws EmailAlreadyExistsException {

        if (emailChecker.check(user.getEmail())) {

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User repositoryResponse = userRepository.save(user);
            if(repositoryResponse != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.add("Content-Type", "application/json; charset=UTF-8");
                ResponseEntity<User> responseEntity = new ResponseEntity<User>(repositoryResponse, headers, HttpStatus.CREATED);
                return responseEntity;
            }
            else
                return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);

        } else
            throw new EmailAlreadyExistsException("Email already exists: Email=" + user.getEmail());

    }

    @DeleteMapping(value = "/{id}")
    public void deleteUser(@PathVariable int id) {
        try {
            userRepository.delete(Long.valueOf(id));
            return;
        }
        catch (Exception e){
            return;
        }
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public String returnBadRequest(EmailAlreadyExistsException ex) {
        return ex.getMessage();
    }
}
