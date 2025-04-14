package com.samueldev.course_springboot.repositories;

import com.samueldev.course_springboot.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
