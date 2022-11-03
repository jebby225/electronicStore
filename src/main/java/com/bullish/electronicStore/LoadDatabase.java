package com.bullish.electronicStore;

import com.bullish.electronicStore.model.Product;
import com.bullish.electronicStore.model.User;
import com.bullish.electronicStore.enums.UserRoles;
import com.bullish.electronicStore.repository.ProductRepository;
import com.bullish.electronicStore.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(ProductRepository productRepository, UserRepository userRepository) {

        return args -> {
            Product product = new Product("fan1","fan", "burglar", 3., "abx");
            User user = new User("alice", "alice@bullish.com", UserRoles.CUSTOMER);

         //   log.info("Preloading " + productRepository.save(product));
         //   log.info("Preloading " + productRepository.save(new Product("cooker1", "cooker", "burglar", 4., "abx")));
         //   log.info("Preloading " + userRepository.save(user));

        };
    }
}
