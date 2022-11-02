package com.bullish.electronicStore;

import com.bullish.electronicStore.common.ApiResponse;
import com.bullish.electronicStore.controller.ProductController;
import com.bullish.electronicStore.entity.Product;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

@SpringBootTest(classes = { ElectronicStoreApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired private ProductController productController;

    @Test
    public void contextLoads() {
        Assertions
                .assertThat(productController)
                .isNotNull();
    }


    @Test
    public void testGetAllProducts() {
        ResponseEntity<Iterable<Product>> responseEntity =
                restTemplate.exchange("http://localhost:8080/api/products/list", HttpMethod.GET,
                        null, new ParameterizedTypeReference<Iterable<Product>>() {
        });
        Iterable<Product> products = responseEntity.getBody();
        Assertions
                .assertThat(products)
                .hasSize(2);

        assertThat(products)
                .extracting("name")
                .contains("fan", "cooker");
    }

    @Test
    public void testAddNewProduct() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<Product> responseEntity = restTemplate.postForEntity(
                "http://localhost:8080/api/products/add",
                new HttpEntity<>(new Product("coffee1", "coffee machine", "coffeeMachine.jpg", 100.0, "capsule"), headers),
                Product.class);

        Product p = responseEntity.getBody();
        Assertions
                .assertThat(responseEntity.getStatusCode())
                .isEqualByComparingTo(HttpStatus.OK);

    }


    // addexistingproduct
    // deleteNonexistingProduct
}

