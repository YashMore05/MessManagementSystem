package com.messmanagement.entity;

import com.messmanagement.enums.LeaveStatus;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class LeaveRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private User student;
    @ManyToOne(optional = false)
    private Mess mess;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String reason;
    @Enumerated(EnumType.STRING)
    private LeaveStatus status;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        status = LeaveStatus.MARKED;
    }
    public static class LeaveRequestBuilder {
        private Long id;
        private User student;
        private Mess mess;
        private LocalDate fromDate;
        private LocalDate toDate;
        private String reason;
        private LeaveStatus status;
        private LocalDateTime createdAt;
        LeaveRequestBuilder() {
        }

        
        public LeaveRequest.LeaveRequestBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        
        public LeaveRequest.LeaveRequestBuilder student(final User student) {
            this.student = student;
            return this;
        }

        
        public LeaveRequest.LeaveRequestBuilder mess(final Mess mess) {
            this.mess = mess;
            return this;
        }

        
        public LeaveRequest.LeaveRequestBuilder fromDate(final LocalDate fromDate) {
            this.fromDate = fromDate;
            return this;
        }

        
        public LeaveRequest.LeaveRequestBuilder toDate(final LocalDate toDate) {
            this.toDate = toDate;
            return this;
        }

        
        public LeaveRequest.LeaveRequestBuilder reason(final String reason) {
            this.reason = reason;
            return this;
        }

        
        public LeaveRequest.LeaveRequestBuilder status(final LeaveStatus status) {
            this.status = status;
            return this;
        }

        
        public LeaveRequest.LeaveRequestBuilder createdAt(final LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }
        public LeaveRequest build() {
            return new LeaveRequest(this.id, this.student, this.mess, this.fromDate, this.toDate, this.reason, this.status, this.createdAt);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "LeaveRequest.LeaveRequestBuilder(id=" + this.id + ", student=" + this.student + ", mess=" + this.mess + ", fromDate=" + this.fromDate + ", toDate=" + this.toDate + ", reason=" + this.reason + ", status=" + this.status + ", createdAt=" + this.createdAt + ")";
        }
    }
    public static LeaveRequest.LeaveRequestBuilder builder() {
        return new LeaveRequest.LeaveRequestBuilder();
    }
    public Long getId() {
        return this.id;
    }
    public User getStudent() {
        return this.student;
    }
    public Mess getMess() {
        return this.mess;
    }
    public LocalDate getFromDate() {
        return this.fromDate;
    }
    public LocalDate getToDate() {
        return this.toDate;
    }
    public String getReason() {
        return this.reason;
    }
    public LeaveStatus getStatus() {
        return this.status;
    }
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
    public void setId(final Long id) {
        this.id = id;
    }
    public void setStudent(final User student) {
        this.student = student;
    }
    public void setMess(final Mess mess) {
        this.mess = mess;
    }
    public void setFromDate(final LocalDate fromDate) {
        this.fromDate = fromDate;
    }
    public void setToDate(final LocalDate toDate) {
        this.toDate = toDate;
    }
    public void setReason(final String reason) {
        this.reason = reason;
    }
    public void setStatus(final LeaveStatus status) {
        this.status = status;
    }
    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LeaveRequest() {
    }
    public LeaveRequest(final Long id, final User student, final Mess mess, final LocalDate fromDate, final LocalDate toDate, final String reason, final LeaveStatus status, final LocalDateTime createdAt) {
        this.id = id;
        this.student = student;
        this.mess = mess;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.reason = reason;
        this.status = status;
        this.createdAt = createdAt;
    }
}
