package com.samueldev.course_springboot.repositories;

import com.samueldev.course_springboot.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
