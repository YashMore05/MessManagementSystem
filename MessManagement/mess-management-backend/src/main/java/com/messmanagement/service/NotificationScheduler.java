package com.messmanagement.service;

import com.messmanagement.entity.Notification;
import com.messmanagement.entity.Subscription;
import com.messmanagement.repository.NotificationRepository;
import com.messmanagement.repository.SubscriptionRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class NotificationScheduler {
    private final SubscriptionRepository subscriptionRepository;
    private final NotificationRepository notificationRepository;

    public NotificationScheduler(SubscriptionRepository subscriptionRepository, NotificationRepository notificationRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.notificationRepository = notificationRepository;
    }

    @Scheduled(cron = "0 0 8 * * *")
    public void subscriptionExpiryReminders() {
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        for (Subscription subscription : subscriptionRepository.findAll()) {
            if (tomorrow.equals(subscription.getEndDate())) {
                notificationRepository.save(Notification.builder()
                        .user(subscription.getStudent())
                        .title("Subscription expires tomorrow")
                        .message("Your plan for " + subscription.getMess().getName() + " expires tomorrow.")
                        .readFlag(false)
                        .build());
            }
        }
    }
}
