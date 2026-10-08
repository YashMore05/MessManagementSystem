package com.messmanagement.repository;

import com.messmanagement.entity.Mess;
import com.messmanagement.entity.User;
import com.messmanagement.enums.MessType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessRepository extends JpaRepository<Mess, Long> {
    List<Mess> findByProvider(User provider);
    List<Mess> findByAreaContainingIgnoreCaseAndActiveTrue(String area);
    List<Mess> findByMessTypeAndActiveTrue(MessType messType);
    List<Mess> findByActiveTrue();
    long countByActiveTrue();
}
