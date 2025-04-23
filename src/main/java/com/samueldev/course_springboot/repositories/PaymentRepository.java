package com.samueldev.course_springboot.repositories;

import com.samueldev.course_springboot.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
