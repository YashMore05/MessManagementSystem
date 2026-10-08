package com.messmanagement.entity;

import com.messmanagement.enums.PaymentStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private User user;
    private String purpose;
    private String razorpayOrderId;
    private String razorpayPaymentId;
    private String razorpaySignature;
    @Column(nullable = false)
    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
    public static class PaymentBuilder {
        private Long id;
        private User user;
        private String purpose;
        private String razorpayOrderId;
        private String razorpayPaymentId;
        private String razorpaySignature;
        private BigDecimal amount;
        private PaymentStatus status;
        private LocalDateTime createdAt;
        PaymentBuilder() {
        }

        
        public Payment.PaymentBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        
        public Payment.PaymentBuilder user(final User user) {
            this.user = user;
            return this;
        }

        
        public Payment.PaymentBuilder purpose(final String purpose) {
            this.purpose = purpose;
            return this;
        }

        
        public Payment.PaymentBuilder razorpayOrderId(final String razorpayOrderId) {
            this.razorpayOrderId = razorpayOrderId;
            return this;
        }

        
        public Payment.PaymentBuilder razorpayPaymentId(final String razorpayPaymentId) {
            this.razorpayPaymentId = razorpayPaymentId;
            return this;
        }

        
        public Payment.PaymentBuilder razorpaySignature(final String razorpaySignature) {
            this.razorpaySignature = razorpaySignature;
            return this;
        }

        
        public Payment.PaymentBuilder amount(final BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        
        public Payment.PaymentBuilder status(final PaymentStatus status) {
            this.status = status;
            return this;
        }

        
        public Payment.PaymentBuilder createdAt(final LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }
        public Payment build() {
            return new Payment(this.id, this.user, this.purpose, this.razorpayOrderId, this.razorpayPaymentId, this.razorpaySignature, this.amount, this.status, this.createdAt);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "Payment.PaymentBuilder(id=" + this.id + ", user=" + this.user + ", purpose=" + this.purpose + ", razorpayOrderId=" + this.razorpayOrderId + ", razorpayPaymentId=" + this.razorpayPaymentId + ", razorpaySignature=" + this.razorpaySignature + ", amount=" + this.amount + ", status=" + this.status + ", createdAt=" + this.createdAt + ")";
        }
    }
    public static Payment.PaymentBuilder builder() {
        return new Payment.PaymentBuilder();
    }
    public Long getId() {
        return this.id;
    }
    public User getUser() {
        return this.user;
    }
    public String getPurpose() {
        return this.purpose;
    }
    public String getRazorpayOrderId() {
        return this.razorpayOrderId;
    }
    public String getRazorpayPaymentId() {
        return this.razorpayPaymentId;
    }
    public String getRazorpaySignature() {
        return this.razorpaySignature;
    }
    public BigDecimal getAmount() {
        return this.amount;
    }
    public PaymentStatus getStatus() {
        return this.status;
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
    public void setPurpose(final String purpose) {
        this.purpose = purpose;
    }
    public void setRazorpayOrderId(final String razorpayOrderId) {
        this.razorpayOrderId = razorpayOrderId;
    }
    public void setRazorpayPaymentId(final String razorpayPaymentId) {
        this.razorpayPaymentId = razorpayPaymentId;
    }
    public void setRazorpaySignature(final String razorpaySignature) {
        this.razorpaySignature = razorpaySignature;
    }
    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }
    public void setStatus(final PaymentStatus status) {
        this.status = status;
    }
    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public Payment() {
    }
    public Payment(final Long id, final User user, final String purpose, final String razorpayOrderId, final String razorpayPaymentId, final String razorpaySignature, final BigDecimal amount, final PaymentStatus status, final LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.purpose = purpose;
        this.razorpayOrderId = razorpayOrderId;
        this.razorpayPaymentId = razorpayPaymentId;
        this.razorpaySignature = razorpaySignature;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
    }
}
