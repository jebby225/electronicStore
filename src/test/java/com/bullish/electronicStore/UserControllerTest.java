package com.bullish.electronicStore;

import com.bullish.electronicStore.controller.UserController;
import com.bullish.electronicStore.model.User;
import com.bullish.electronicStore.enums.UserRoles;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = { ElectronicStoreApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired private UserController userController;

    @Test
    public void contextLoads() {
        Assertions
                .assertThat(userController)
                .isNotNull();
    }


    @Test
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

    }


    // addexistingproduct
    // deleteNonexistingProduct
}


