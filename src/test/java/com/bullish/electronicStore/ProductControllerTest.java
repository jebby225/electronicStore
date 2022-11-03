package com.bullish.electronicStore;

import com.bullish.electronicStore.controller.ProductController;
import com.bullish.electronicStore.model.Product;
import com.bullish.electronicStore.model.ProductDiscount;
import com.bullish.electronicStore.repository.ProductRepository;
import com.bullish.electronicStore.service.ProductService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;
import java.util.Optional;

@SpringBootTest(classes = { ElectronicStoreApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductController productController;

    Product cm1 = new Product("cm1", "coffee Machine", "coffeemachine_1.jpg", 100.0, "coffee machine #1");
    Product cm2 = new Product("cm2", "coffee Machine", "coffeemachine_2.jpg", 100.0, "coffee machine #2");

    @Test
    public void testCanAddNewProducts() {
        productService.addProduct(cm1);
        productService.addProduct(cm2);
        List<Product> products = productRepository.findAll();
        Assertions
                .assertThat(products)
                .hasSize(2);
        assertThat(products)
                .extracting("code")
                .contains("cm1", "cm2");
    }

    @Test
    public void testAddDiscountCode() {
        productService.addProduct(cm1);
        Product product = productRepository.findByCode("cm1");
        Assertions
                .assertThat(product)
                .isNotNull();

        product = productService.addProductDiscount(product.getId(), new ProductDiscount(90.0, true, 2, 1, "buy 2 get next 1 10% off"));
        Assertions
                .assertThat(product.getProductDiscount())
                .isNotNull();
    }
    @Test
    public void testGetAllProducts() {
        List<Product> products = productRepository.findAll();
        productRepository.findByCode("cm1");
        products = productRepository.findAll();

        Assertions
                .assertThat(products)
                .hasSize(0);

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

