package com.messmanagement.repository;

import com.messmanagement.entity.Mess;
import com.messmanagement.entity.Subscription;
import com.messmanagement.entity.User;
import com.messmanagement.enums.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByStudent(User student);
    List<Subscription> findByMess(Mess mess);
    List<Subscription> findByMessAndStatus(Mess mess, SubscriptionStatus status);
    long countByStatus(SubscriptionStatus status);
}
