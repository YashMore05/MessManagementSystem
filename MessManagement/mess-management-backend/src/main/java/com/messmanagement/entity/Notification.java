package com.messmanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private User user;
    private String title;
    private String message;
    private boolean readFlag;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
    public static class NotificationBuilder {
        private Long id;
        private User user;
        private String title;
        private String message;
        private boolean readFlag;
        private LocalDateTime createdAt;
        NotificationBuilder() {
        }

        
        public Notification.NotificationBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        
        public Notification.NotificationBuilder user(final User user) {
            this.user = user;
            return this;
        }

        
        public Notification.NotificationBuilder title(final String title) {
            this.title = title;
            return this;
        }

        
        public Notification.NotificationBuilder message(final String message) {
            this.message = message;
            return this;
        }

        
        public Notification.NotificationBuilder readFlag(final boolean readFlag) {
            this.readFlag = readFlag;
            return this;
        }

        
        public Notification.NotificationBuilder createdAt(final LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }
        public Notification build() {
            return new Notification(this.id, this.user, this.title, this.message, this.readFlag, this.createdAt);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "Notification.NotificationBuilder(id=" + this.id + ", user=" + this.user + ", title=" + this.title + ", message=" + this.message + ", readFlag=" + this.readFlag + ", createdAt=" + this.createdAt + ")";
        }
    }
    public static Notification.NotificationBuilder builder() {
        return new Notification.NotificationBuilder();
    }
    public Long getId() {
        return this.id;
    }
    public User getUser() {
        return this.user;
    }
    public String getTitle() {
        return this.title;
    }
    public String getMessage() {
        return this.message;
    }
    public boolean isReadFlag() {
        return this.readFlag;
    }
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
    public void setId(final Long id) {
        this.id = id;
    }
    public void setUser(final User user) {
        this.user = user;
    }
    public void setTitle(final String title) {
        this.title = title;
    }
    public void setMessage(final String message) {
        this.message = message;
    }
    public void setReadFlag(final boolean readFlag) {
        this.readFlag = readFlag;
    }
    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public Notification() {
    }
    public Notification(final Long id, final User user, final String title, final String message, final boolean readFlag, final LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.message = message;
        this.readFlag = readFlag;
        this.createdAt = createdAt;
    }
}
