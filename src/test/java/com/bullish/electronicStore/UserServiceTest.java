package com.bullish.electronicStore;

import com.bullish.electronicStore.exception.CustomException;
import com.bullish.electronicStore.model.User;
import com.bullish.electronicStore.enums.UserRoles;
import com.bullish.electronicStore.repository.UserRepository;
import com.bullish.electronicStore.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest(classes = { ElectronicStoreApplication.class })
public class UserServiceTest {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    User user1 = new User("alice", "alice@bullish.com", UserRoles.CUSTOMER);
    User user2 = new User("bob", "bob@bullish.com", UserRoles.CUSTOMER);

    @Test
    public void testCanAddNewUser_exceptionWhenAddExisting() {
        userService.addUser(user1);
        userService.addUser(user2);
        List<User> users = userRepository.findAll();
        Assertions
                .assertThat(users)
                .hasSize(2);
        assertThat(users)
                .extracting("email")
                .contains("alice@bullish.com", "bob@bullish.com");

        // try adding a user with existing email
        assertThatThrownBy(() -> {
            userService.addUser(new User("bobby", "bob@bullish.com", UserRoles.ADMIN));
        }).isInstanceOf(CustomException.class)
                .hasMessageContaining("User with email bob@bullish.com already exists");

    }

    @Test
    public void testDeleteUsers_ExistAndNonExist() {

        // Add a new user
        userService.addUser(user1);
        List<User> users = userRepository.findAll();
        Assertions
                .assertThat(users)
                .hasSize(1);

        // Delete the above user
        userService.deleteUserById(users.get(0).getId());
        users = userRepository.findAll();
        Assertions
                .assertThat(users)
                .hasSize(0);

        // Delete a user that does not exist
        assertThatThrownBy(() -> {
            userService.deleteUserById(123);
        }).isInstanceOf(CustomException.class)
                .hasMessageContaining("User 123 does not exist");
    }

   /* @Test
    public void testGetAllUsers() {
        ResponseEntity<Iterable<User>> responseEntity =
                restTemplate.exchange("http://localhost:8080/api/users/list", HttpMethod.GET,
                        null, new ParameterizedTypeReference<Iterable<User>>() {
                        });
        Iterable<User> users = responseEntity.getBody();
        Assertions
                .assertThat(users)
                .hasSize(1);

        assertThat(users)
                .extracting("name")
                .contains("alice");
    }

    @Test
    public void testAddNewUser() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<User> responseEntity = restTemplate.postForEntity(
                "http://localhost:8080/api/users/add",
                new HttpEntity<>(new User("alice", "alice@abc.com", UserRoles.CUSTOMER), headers),
                User.class);

        User user = responseEntity.getBody();
        Assertions
                .assertThat(responseEntity.getStatusCode())
                .isEqualByComparingTo(HttpStatus.OK);

    } */

}


