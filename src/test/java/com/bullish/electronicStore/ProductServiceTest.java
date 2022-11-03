package com.bullish.electronicStore;

import com.bullish.electronicStore.enums.DiscountAmountUnit;
import com.bullish.electronicStore.enums.DiscountType;
import com.bullish.electronicStore.exception.CustomException;
import com.bullish.electronicStore.model.Product;
import com.bullish.electronicStore.model.ProductDiscount;
import com.bullish.electronicStore.repository.ProductRepository;
import com.bullish.electronicStore.service.ProductService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

@SpringBootTest(classes = { ElectronicStoreApplication.class })
public class ProductServiceTest {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;

    Product product1 = new Product("cm1", "coffee Machine", "coffeemachine_1.jpg", 100.0, "coffee machine #1");
    Product product2 = new Product("cm2", "coffee Machine", "coffeemachine_2.jpg", 100.0, "coffee machine #2");

    @BeforeEach
    public void init() {
        productRepository.deleteAll();
    }
    @Test
    public void testCanAddNewProducts_exceptionWhenAddExisting() {
        productService.addProduct(product1);
        productService.addProduct(product2);
        List<Product> products = productRepository.findAll();
        Assertions
                .assertThat(products)
                .hasSize(2);
        assertThat(products)
                .extracting("code")
                .contains("cm1", "cm2");

        // try adding a product with existing code
        assertThatThrownBy(() -> {
            productService.addProduct(new Product("cm2", "coffee Machine33", "coffeemachine_2.jpg", 100.0, "coffee machine #2"));
        }).isInstanceOf(DataIntegrityViolationException.class)
                .hasMessageContaining("could not execute statement");
    }

    @Test
    public void testAddAndUpdateDiscountCode() {
        productService.addProduct(product1);
        Product product = productRepository.findByCode("cm1");
        Assertions
                .assertThat(product)
                .isNotNull();

        // Apply discount code
        product = productService.addProductDiscount(product.getId(), new ProductDiscount(DiscountType.BUY_X_GET_Y_WITH_Z_DISCOUNT, 2, 1, 90.0, DiscountAmountUnit.PERCENT));
        Assertions
                .assertThat(product.getProductDiscount())
                .isNotNull();

        // Update discount code
        product = productService.addProductDiscount(product.getId(), new ProductDiscount(DiscountType.BUY_X_GET_Y_WITH_Z_DISCOUNT, 2, 1, 80.0, DiscountAmountUnit.PERCENT));
        Assertions
                .assertThat(product.getProductDiscount())
                .hasFieldOrPropertyWithValue("discountAmount", 80.0);
    }

    @Test
    public void testDeleteProducts_NonExistingProduct() {
        // Delete a product that does not exist
        assertThatThrownBy(() -> {
            productService.deleteProductById(123);
        }).isInstanceOf(CustomException.class)
                .hasMessageContaining("Product 123 does not exist");
    }

    @Test
    public void testDeleteProducts_ExistingProduct() {
        // Add a new product
        productService.addProduct(product1);
        List<Product> products = productRepository.findAll();
        Assertions
                .assertThat(products)
                .hasSize(1);

        // Delete the above product
        productService.deleteProductById(products.get(0).getId());
        products = productRepository.findAll();
        Assertions
                .assertThat(products)
                .hasSize(0);
    }

   /*  @Test
    public void testAddNewProduct() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        ResponseEntity<Product> responseEntity = restTemplate.postForEntity(
                "http://localhost:8080/api/products",
                new HttpEntity<>(new Product("coffee1", "coffee machine", "coffeeMachine.jpg", 100.0, "capsule"), headers),
                Product.class);

        Product p = responseEntity.getBody();
        Assertions
                .assertThat(responseEntity.getStatusCode())
                .isEqualByComparingTo(HttpStatus.OK);

    } */

}

