package com.samueldev.course_springboot.repositories;

import com.samueldev.course_springboot.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
