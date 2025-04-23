package com.samueldev.course_springboot.resources;

import com.samueldev.course_springboot.entities.User;
import com.samueldev.course_springboot.services.CategoryService;
import com.samueldev.course_springboot.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

    @Autowired
    private UserService service;
    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<User>> findAll() {
        List<User> list = service.findAll();
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
         User object = service.findById(id);
         return ResponseEntity.ok().body(object);
    }

    @PostMapping
    public ResponseEntity<User> insertUser(@RequestBody User user){
        user = service.insert(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
                path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(user);
    }
}
