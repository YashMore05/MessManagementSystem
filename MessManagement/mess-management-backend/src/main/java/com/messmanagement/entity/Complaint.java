package com.messmanagement.entity;

import com.messmanagement.enums.ComplaintCategory;
import com.messmanagement.enums.ComplaintStatus;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private User student;
    @ManyToOne(optional = false)
    private Mess mess;
    @ManyToOne
    private User assignedProvider;
    @Enumerated(EnumType.STRING)
    private ComplaintCategory category;
    @Enumerated(EnumType.STRING)
    private ComplaintStatus status;
    private String title;
    @Column(length = 2000)
    private String description;
    private LocalDateTime slaDueAt;
    private LocalDateTime createdAt;
    private LocalDateTime resolvedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        status = ComplaintStatus.OPEN;
    }
    public static class ComplaintBuilder {
        private Long id;
        private User student;
        private Mess mess;
        private User assignedProvider;
        private ComplaintCategory category;
        private ComplaintStatus status;
        private String title;
        private String description;
        private LocalDateTime slaDueAt;
        private LocalDateTime createdAt;
        private LocalDateTime resolvedAt;
        ComplaintBuilder() {
        }

        
        public Complaint.ComplaintBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        
        public Complaint.ComplaintBuilder student(final User student) {
            this.student = student;
            return this;
        }

        
        public Complaint.ComplaintBuilder mess(final Mess mess) {
            this.mess = mess;
            return this;
        }

        
        public Complaint.ComplaintBuilder assignedProvider(final User assignedProvider) {
            this.assignedProvider = assignedProvider;
            return this;
        }

        
        public Complaint.ComplaintBuilder category(final ComplaintCategory category) {
            this.category = category;
            return this;
        }

        
        public Complaint.ComplaintBuilder status(final ComplaintStatus status) {
            this.status = status;
            return this;
        }

        
        public Complaint.ComplaintBuilder title(final String title) {
            this.title = title;
            return this;
        }

        
        public Complaint.ComplaintBuilder description(final String description) {
            this.description = description;
            return this;
        }

        
        public Complaint.ComplaintBuilder slaDueAt(final LocalDateTime slaDueAt) {
            this.slaDueAt = slaDueAt;
            return this;
        }

        
        public Complaint.ComplaintBuilder createdAt(final LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        
        public Complaint.ComplaintBuilder resolvedAt(final LocalDateTime resolvedAt) {
            this.resolvedAt = resolvedAt;
            return this;
        }
        public Complaint build() {
            return new Complaint(this.id, this.student, this.mess, this.assignedProvider, this.category, this.status, this.title, this.description, this.slaDueAt, this.createdAt, this.resolvedAt);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "Complaint.ComplaintBuilder(id=" + this.id + ", student=" + this.student + ", mess=" + this.mess + ", assignedProvider=" + this.assignedProvider + ", category=" + this.category + ", status=" + this.status + ", title=" + this.title + ", description=" + this.description + ", slaDueAt=" + this.slaDueAt + ", createdAt=" + this.createdAt + ", resolvedAt=" + this.resolvedAt + ")";
        }
    }
    public static Complaint.ComplaintBuilder builder() {
        return new Complaint.ComplaintBuilder();
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
    public User getAssignedProvider() {
        return this.assignedProvider;
    }
    public ComplaintCategory getCategory() {
        return this.category;
    }
    public ComplaintStatus getStatus() {
        return this.status;
    }
    public String getTitle() {
        return this.title;
    }
    public String getDescription() {
        return this.description;
    }
    public LocalDateTime getSlaDueAt() {
        return this.slaDueAt;
    }
    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }
    public LocalDateTime getResolvedAt() {
        return this.resolvedAt;
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
    public void setAssignedProvider(final User assignedProvider) {
        this.assignedProvider = assignedProvider;
    }
    public void setCategory(final ComplaintCategory category) {
        this.category = category;
    }
    public void setStatus(final ComplaintStatus status) {
        this.status = status;
    }
    public void setTitle(final String title) {
        this.title = title;
    }
    public void setDescription(final String description) {
        this.description = description;
    }
    public void setSlaDueAt(final LocalDateTime slaDueAt) {
        this.slaDueAt = slaDueAt;
    }
    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public void setResolvedAt(final LocalDateTime resolvedAt) {
        this.resolvedAt = resolvedAt;
    }
    public Complaint() {
    }
    public Complaint(final Long id, final User student, final Mess mess, final User assignedProvider, final ComplaintCategory category, final ComplaintStatus status, final String title, final String description, final LocalDateTime slaDueAt, final LocalDateTime createdAt, final LocalDateTime resolvedAt) {
        this.id = id;
        this.student = student;
        this.mess = mess;
        this.assignedProvider = assignedProvider;
        this.category = category;
        this.status = status;
        this.title = title;
        this.description = description;
        this.slaDueAt = slaDueAt;
        this.createdAt = createdAt;
        this.resolvedAt = resolvedAt;
    }
}
