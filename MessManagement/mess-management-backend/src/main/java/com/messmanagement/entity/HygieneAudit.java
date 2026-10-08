package com.messmanagement.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class HygieneAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private Mess mess;
    private LocalDate auditMonth;
    private Integer kitchenScore;
    private Integer staffScore;
    private Integer storageScore;
    private Integer overallScore;
    private String redFlags;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
    public static class HygieneAuditBuilder {
        private Long id;
        private Mess mess;
        private LocalDate auditMonth;
        private Integer kitchenScore;
        private Integer staffScore;
        private Integer storageScore;
        private Integer overallScore;
        private String redFlags;
        private LocalDateTime createdAt;
        HygieneAuditBuilder() {
        }

        
        public HygieneAudit.HygieneAuditBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        
        public HygieneAudit.HygieneAuditBuilder mess(final Mess mess) {
            this.mess = mess;
            return this;
        }

        
        public HygieneAudit.HygieneAuditBuilder auditMonth(final LocalDate auditMonth) {
            this.auditMonth = auditMonth;
            return this;
        }

        
        public HygieneAudit.HygieneAuditBuilder kitchenScore(final Integer kitchenScore) {
            this.kitchenScore = kitchenScore;
            return this;
        }

        
        public HygieneAudit.HygieneAuditBuilder staffScore(final Integer staffScore) {
            this.staffScore = staffScore;
            return this;
        }

        
        public HygieneAudit.HygieneAuditBuilder storageScore(final Integer storageScore) {
            this.storageScore = storageScore;
            return this;
        }

        
        public HygieneAudit.HygieneAuditBuilder overallScore(final Integer overallScore) {
            this.overallScore = overallScore;
            return this;
        }

        
        public HygieneAudit.HygieneAuditBuilder redFlags(final String redFlags) {
            this.redFlags = redFlags;
            return this;
        }

        
        public HygieneAudit.HygieneAuditBuilder createdAt(final LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }
        public HygieneAudit build() {
            return new HygieneAudit(this.id, this.mess, this.auditMonth, this.kitchenScore, this.staffScore, this.storageScore, this.overallScore, this.redFlags, this.createdAt);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "HygieneAudit.HygieneAuditBuilder(id=" + this.id + ", mess=" + this.mess + ", auditMonth=" + this.auditMonth + ", kitchenScore=" + this.kitchenScore + ", staffScore=" + this.staffScore + ", storageScore=" + this.storageScore + ", overallScore=" + this.overallScore + ", redFlags=" + this.redFlags + ", createdAt=" + this.createdAt + ")";
        }
    }
    public static HygieneAudit.HygieneAuditBuilder builder() {
        return new HygieneAudit.HygieneAuditBuilder();
    }
    public Long getId() {
        return this.id;
    }
    public Mess getMess() {
        return this.mess;
    }
    public LocalDate getAuditMonth() {
        return this.auditMonth;
    }
    public Integer getKitchenScore() {
        return this.kitchenScore;
    }
    public Integer getStaffScore() {
        return this.staffScore;
    }
    public Integer getStorageScore() {
        return this.storageScore;
    }
    public Integer getOverallScore() {
        return this.overallScore;
    }
    public String getRedFlags() {
        return this.redFlags;
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
    public void setAuditMonth(final LocalDate auditMonth) {
        this.auditMonth = auditMonth;
    }
    public void setKitchenScore(final Integer kitchenScore) {
        this.kitchenScore = kitchenScore;
    }
    public void setStaffScore(final Integer staffScore) {
        this.staffScore = staffScore;
    }
    public void setStorageScore(final Integer storageScore) {
        this.storageScore = storageScore;
    }
    public void setOverallScore(final Integer overallScore) {
        this.overallScore = overallScore;
    }
    public void setRedFlags(final String redFlags) {
        this.redFlags = redFlags;
    }
    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public HygieneAudit() {
    }
    public HygieneAudit(final Long id, final Mess mess, final LocalDate auditMonth, final Integer kitchenScore, final Integer staffScore, final Integer storageScore, final Integer overallScore, final String redFlags, final LocalDateTime createdAt) {
        this.id = id;
        this.mess = mess;
        this.auditMonth = auditMonth;
        this.kitchenScore = kitchenScore;
        this.staffScore = staffScore;
        this.storageScore = storageScore;
        this.overallScore = overallScore;
        this.redFlags = redFlags;
        this.createdAt = createdAt;
    }
}
