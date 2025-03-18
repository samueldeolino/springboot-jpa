package com.samueldev.course_springboot.resources;
import com.samueldev.course_springboot.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    @GetMapping
    public ResponseEntity<User> findAll(Long id) {
        User user = new User(1L, "Samuel", "Samuelgmail.com");
        return ResponseEntity.ok().body(user);
    }

//    @PostMapping
//    public ResponseEntity<User> saveUser(@RequestBody User user) {
//        return
//    }
}
