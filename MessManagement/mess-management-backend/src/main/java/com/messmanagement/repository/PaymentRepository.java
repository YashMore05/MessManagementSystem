package com.messmanagement.repository;

import com.messmanagement.entity.Payment;
import com.messmanagement.entity.User;
import com.messmanagement.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByRazorpayOrderId(String razorpayOrderId);
    List<Payment> findByUser(User user);
    List<Payment> findByStatus(PaymentStatus status);
}
