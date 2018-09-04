package ru.orindev.BostonGeneTest.SpringUserService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import ru.orindev.BostonGeneTest.SpringUserService.model.User;

import java.sql.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void WhenCreatingUser_ThenGotCreated() {
        User userToAdd = createTestUser(1, "test1@email.com");
        ResponseEntity<User> responseEntity =
                restTemplate.postForEntity("/user", userToAdd, User.class);
        User userFromResponse = responseEntity.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(userToAdd.getName(), userFromResponse.getName());
    }

    @Test
    public void WhenTryToAddUserWithExistingEmail_ThenExpectEmailAlreadyExistsException() {
        User userToAdd = createTestUser(2, "test2@email.com");
        User userWithSameEmail = createTestUser(3, "test2@email.com");
        restTemplate.postForObject("/user", userToAdd, String.class);
        String result = restTemplate.postForObject("/user", userWithSameEmail, String.class);
        assertTrue(result.contains("Email already exists: Email=test2@email.com"));
    }

    @Test
    public void WhenTryingToDeleteExistingUser_ThenUserNotFoundException() {
        User userToAdd = createTestUser(4, "test3@email.com");

        restTemplate.postForObject("/user", userToAdd, String.class);
        restTemplate.delete("/user/" + 4);

        String response =
                restTemplate.getForObject("/user?email=" + userToAdd.getEmail(), String.class);
        assertTrue(response.contains("пользователя с таким Email нет"));
    }

    @Test
    public void WhenTryingGetExistingUser_ThenGotUser() {
        User userToAdd = createTestUser(5, "test4@email.com");
        restTemplate.postForEntity("/user", userToAdd, User.class);

        ResponseEntity<User> responseEntity =
                restTemplate.getForEntity("/user?email=" + userToAdd.getEmail(), User.class);
        User user = responseEntity.getBody();
        assertEquals(user.getName(), userToAdd.getName());
    }

    @Test
    public void WhenTryingGetNotExistingUser_ThenGotException() {
        User userToAdd = createTestUser(100500, "jigurda@j.j");

            String response =
                restTemplate.getForObject("/user?email=" + userToAdd.getEmail(), String.class);
        assertTrue(response.contains("пользователя с таким Email нет"));
    }


    private User createTestUser(int id, String email) {
        User frodo = new User();
        frodo.setId(Long.valueOf(id));
        frodo.setName("Frodo");
        frodo.setSecondName("Baggins");
        frodo.setBirthDate(Date.valueOf("1000-01-01"));
        frodo.setEmail(email);
        frodo.setPassword("password");
        return frodo;
    }

}
