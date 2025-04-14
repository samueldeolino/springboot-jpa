package com.samueldev.course_springboot.config;

import com.samueldev.course_springboot.entities.Order;
import com.samueldev.course_springboot.entities.User;
import com.samueldev.course_springboot.entities.enums.OrderStatus;
import com.samueldev.course_springboot.repositories.OrderRepository;
import com.samueldev.course_springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Instant;
import java.util.Arrays;

@Configuration
@Profile("postgre")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void run(String... args) throws Exception {
        User user1 = new User(null, "John", "Jhon@gmail.com", "jhon123", "5512345678");
        User user2 = new User(null, "Lari", "Lari@gmail.com", "lari123", "55554444");
        userRepository.saveAll(Arrays.asList(user1, user2));

        Order order1 = new Order(null, Instant.parse("2025-04-14T10:06:20Z"), OrderStatus.DELIVERED, user1);
        Order order2 = new Order(null, Instant.parse("2025-04-06T11:32:20Z"), OrderStatus.PAID, user2);
        Order order3 = new Order(null, Instant.parse("2025-04-10T10:06:20Z"), OrderStatus.WAYITING_PAYMENT, user1);

        orderRepository.saveAll(Arrays.asList(order1, order2, order3));
    }
}
