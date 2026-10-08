package com.messmanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private Mess mess;
    private String title;
    @Column(length = 2000)
    private String message;
    private LocalDate expiryDate;
    private Integer readCount;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        readCount = 0;
    }
    public static class NoticeBuilder {
        private Long id;
        private Mess mess;
        private String title;
        private String message;
        private LocalDate expiryDate;
        private Integer readCount;
        private LocalDateTime createdAt;
        NoticeBuilder() {
        }

        
        public Notice.NoticeBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        
        public Notice.NoticeBuilder mess(final Mess mess) {
            this.mess = mess;
            return this;
        }

        
        public Notice.NoticeBuilder title(final String title) {
            this.title = title;
            return this;
        }

        
        public Notice.NoticeBuilder message(final String message) {
            this.message = message;
            return this;
        }

        
        public Notice.NoticeBuilder expiryDate(final LocalDate expiryDate) {
            this.expiryDate = expiryDate;
            return this;
        }

        
        public Notice.NoticeBuilder readCount(final Integer readCount) {
            this.readCount = readCount;
            return this;
        }

        
        public Notice.NoticeBuilder createdAt(final LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }
        public Notice build() {
            return new Notice(this.id, this.mess, this.title, this.message, this.expiryDate, this.readCount, this.createdAt);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "Notice.NoticeBuilder(id=" + this.id + ", mess=" + this.mess + ", title=" + this.title + ", message=" + this.message + ", expiryDate=" + this.expiryDate + ", readCount=" + this.readCount + ", createdAt=" + this.createdAt + ")";
        }
    }
    public static Notice.NoticeBuilder builder() {
        return new Notice.NoticeBuilder();
    }
    public Long getId() {
        return this.id;
    }
    public Mess getMess() {
        return this.mess;
    }
    public String getTitle() {
        return this.title;
    }
    public String getMessage() {
        return this.message;
    }
    public LocalDate getExpiryDate() {
        return this.expiryDate;
    }
    public Integer getReadCount() {
        return this.readCount;
    }
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
    public void setId(final Long id) {
        this.id = id;
    }
    public void setMess(final Mess mess) {
        this.mess = mess;
    }
    public void setTitle(final String title) {
        this.title = title;
    }
    public void setMessage(final String message) {
        this.message = message;
    }
    public void setExpiryDate(final LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
    public void setReadCount(final Integer readCount) {
        this.readCount = readCount;
    }
    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public Notice() {
    }
    public Notice(final Long id, final Mess mess, final String title, final String message, final LocalDate expiryDate, final Integer readCount, final LocalDateTime createdAt) {
        this.id = id;
        this.mess = mess;
        this.title = title;
        this.message = message;
        this.expiryDate = expiryDate;
        this.readCount = readCount;
        this.createdAt = createdAt;
    }
}
