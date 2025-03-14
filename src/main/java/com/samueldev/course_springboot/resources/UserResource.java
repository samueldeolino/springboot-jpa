package com.samueldev.course_springboot.resources;
import com.samueldev.course_springboot.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    @GetMapping
    public ResponseEntity<User> findById() {

        User user = new User(1L, "Samuel", "samuel@gmail.com");

        return ResponseEntity.ok().body(user);
    }
}
