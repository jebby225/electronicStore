package com.bullish.electronicStore;

import com.bullish.electronicStore.model.Product;
import com.bullish.electronicStore.model.User;
import com.bullish.electronicStore.service.CartService;
import com.bullish.electronicStore.service.ProductService;
import com.bullish.electronicStore.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = { ElectronicStoreApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CartControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;


    @Autowired private CartService cartService;
    @Autowired private UserService userService;
    @Autowired private ProductService productService;
    //@Autowired private CartCotroller cartCotroller;

    @Test
    public void testAddNewProductService() {
        //Product product = new Product("fan1","fan", "burglar", 3., "abx");
        //User user = new User("alice", "alice@bullish.com", UserRoles.CUSTOMER);

        User user = userService.findUserByEmail("alice@bullish.com");
        Product product = productService.findByProductCode("fan1");

     //   cartService.addToCart(user, new CartItem(product, 0));
    }

    @Test
    public void testAddNewProductService_updateQuantity() {
        //Product product = new Product("fan1","fan", "burglar", 3., "abx");
        //User user = new User("alice", "alice@bullish.com", UserRoles.CUSTOMER);

        User user = userService.findUserByEmail("alice@bullish.com");
        Product product = productService.findByProductCode("fan1");

     ////   cartService.addToCart(user, new CartItem(product, 0));
     ////   cartService.addToCart(user, new CartItem(product, 5));

        Product product2 = productService.findByProductCode("cooker1");
//        cartService.addToCart(user, new CartItem(product2, 10));


  //      cartService.updateCart(1, 2, 7);
    //    cartService.updateCart(1, 2, 0);
    }

    @Test
    public void testAddNewProduct() {
/*
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Product product = new Product("fan1","fan", "burglar", 3., "abx");
        User user = new User("alice", "alice@bullish.com", UserRoles.CUSTOMER);

        MultiValueMap<String, Object> map= new LinkedMultiValueMap<String, Object>();
        map.add("user", user);
        map.add("cartItem", new CartItem(user, product, 0));

        HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<MultiValueMap<String, Object>>(map, headers);

        ResponseEntity<Cart> responseEntity = restTemplate.postForEntity(
                "http://localhost:8080/api/carts/addItem",
                request,
                Cart.class);

        Cart c = responseEntity.getBody();
        Assertions
                .assertThat(responseEntity.getStatusCode())
                .isEqualByComparingTo(HttpStatus.OK);
*/
    }


}
