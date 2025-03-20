package com.samueldev.course_springboot.config;

import com.samueldev.course_springboot.entities.User;
import com.samueldev.course_springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;


    @Override
    public void run(String... args) throws Exception {
        User user1 = new User(null, "John", "Jhon@gmail.com", "jhon123", "5512345678");
        User user2 = new User(null, "Lari", "Lari@gmail.com", "lari123", "55554444");

        userRepository.saveAll(Arrays.asList(user1, user2));
    }
}
