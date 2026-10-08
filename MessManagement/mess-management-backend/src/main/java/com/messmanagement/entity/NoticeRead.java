package com.messmanagement.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"notice_id", "student_id"}))
public class NoticeRead {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Notice notice;

    @ManyToOne(optional = false)
    private User student;

    private LocalDateTime readAt;

    @PrePersist
    public void prePersist() {
        readAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Notice getNotice() {
        return notice;
    }

    public void setNotice(Notice notice) {
        this.notice = notice;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public LocalDateTime getReadAt() {
        return readAt;
    }
}
