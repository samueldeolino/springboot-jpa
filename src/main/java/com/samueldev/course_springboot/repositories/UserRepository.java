package com.samueldev.course_springboot.repositories;

import com.samueldev.course_springboot.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    
    
}
