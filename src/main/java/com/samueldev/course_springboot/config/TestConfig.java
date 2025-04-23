package com.samueldev.course_springboot.config;

import com.samueldev.course_springboot.entities.*;
import com.samueldev.course_springboot.entities.enums.OrderStatus;
import com.samueldev.course_springboot.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Instant;
import java.util.Arrays;

@Configuration
@Profile("postgres")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public void run(String... args) throws Exception {
        User user1 = new User(null, "John", "Jhon@gmail.com", "jhon123", "5512345678");
        User user2 = new User(null, "Lari", "Lari@gmail.com", "lari123", "55554444");

        Order order1 = new Order(null, Instant.parse("2025-04-14T10:06:20Z"), OrderStatus.DELIVERED, user1);
        Order order2 = new Order(null, Instant.parse("2025-04-06T11:32:20Z"), OrderStatus.PAID, user2);
        Order order3 = new Order(null, Instant.parse("2025-04-10T10:06:20Z"), OrderStatus.WAYITING_PAYMENT, user1);

        Category category1 = new Category(null, "Electronic");
        Category category2 = new Category(null, "Food");
        Category category3 = new Category(null, "Computers");

        Product p1 = new Product(null, "Acer nitro V15", "Notebook Gamer", 3200.2, "");
        Product p2 = new Product(null, "Dell Vostro", "Notebook", 23093.99, "");
        Product p3 = new Product(null, "Apple", "Delicious food", 5.0, "");

        userRepository.saveAll(Arrays.asList(user1, user2));
        orderRepository.saveAll(Arrays.asList(order1, order2, order3));
        categoryRepository.saveAll(Arrays.asList(category1, category2, category3));
        productRepository.saveAll(Arrays.asList(p1, p2, p3));

        p1.getCategories().add(category1);
        p1.getCategories().add(category3);
        p2.getCategories().add(category1);
        p2.getCategories().add(category3);
        p3.getCategories().add(category2);

        productRepository.saveAll(Arrays.asList(p1, p2, p3));

        OrderItem orderItem1 = new OrderItem(order1, p1, p1.getPrice(), 1);
        OrderItem orderItem2 = new OrderItem(order2, p3, p3.getPrice(), 2);
        OrderItem orderItem3 = new OrderItem(order1, p1, p1.getPrice(), 1);

        orderItemRepository.saveAll(Arrays.asList(orderItem1, orderItem2, orderItem3));
    }
}
