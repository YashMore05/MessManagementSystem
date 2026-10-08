package com.messmanagement.entity;

import com.messmanagement.enums.PaymentStatus;
import com.messmanagement.enums.SubscriptionStatus;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private User student;
    @ManyToOne(optional = false)
    private MonthlyPlan plan;
    @ManyToOne(optional = false)
    private Mess mess;
    private LocalDate startDate;
    private LocalDate endDate;
    @Column(nullable = false)
    private BigDecimal amountCharged;
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status;
    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;
    public static class SubscriptionBuilder {
        private Long id;
        private User student;
        private MonthlyPlan plan;
        private Mess mess;
        private LocalDate startDate;
        private LocalDate endDate;
        private BigDecimal amountCharged;
        private SubscriptionStatus status;
        private PaymentStatus paymentStatus;
        SubscriptionBuilder() {
        }

        
        public Subscription.SubscriptionBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        
        public Subscription.SubscriptionBuilder student(final User student) {
            this.student = student;
            return this;
        }

        
        public Subscription.SubscriptionBuilder plan(final MonthlyPlan plan) {
            this.plan = plan;
            return this;
        }

        
        public Subscription.SubscriptionBuilder mess(final Mess mess) {
            this.mess = mess;
            return this;
        }

        
        public Subscription.SubscriptionBuilder startDate(final LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        
        public Subscription.SubscriptionBuilder endDate(final LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        
        public Subscription.SubscriptionBuilder amountCharged(final BigDecimal amountCharged) {
            this.amountCharged = amountCharged;
            return this;
        }

        
        public Subscription.SubscriptionBuilder status(final SubscriptionStatus status) {
            this.status = status;
            return this;
        }

        
        public Subscription.SubscriptionBuilder paymentStatus(final PaymentStatus paymentStatus) {
            this.paymentStatus = paymentStatus;
            return this;
        }
        public Subscription build() {
            return new Subscription(this.id, this.student, this.plan, this.mess, this.startDate, this.endDate, this.amountCharged, this.status, this.paymentStatus);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "Subscription.SubscriptionBuilder(id=" + this.id + ", student=" + this.student + ", plan=" + this.plan + ", mess=" + this.mess + ", startDate=" + this.startDate + ", endDate=" + this.endDate + ", amountCharged=" + this.amountCharged + ", status=" + this.status + ", paymentStatus=" + this.paymentStatus + ")";
        }
    }
    public static Subscription.SubscriptionBuilder builder() {
        return new Subscription.SubscriptionBuilder();
    }
    public Long getId() {
        return this.id;
    }
    public User getStudent() {
        return this.student;
    }
    public MonthlyPlan getPlan() {
        return this.plan;
    }
    public Mess getMess() {
        return this.mess;
    }
    public LocalDate getStartDate() {
        return this.startDate;
    }
    public LocalDate getEndDate() {
        return this.endDate;
    }
    public BigDecimal getAmountCharged() {
        return this.amountCharged;
    }
    public SubscriptionStatus getStatus() {
        return this.status;
    }
    public PaymentStatus getPaymentStatus() {
        return this.paymentStatus;
    }
    public void setId(final Long id) {
        this.id = id;
    }
    public void setStudent(final User student) {
        this.student = student;
    }
    public void setPlan(final MonthlyPlan plan) {
        this.plan = plan;
    }
    public void setMess(final Mess mess) {
        this.mess = mess;
    }
    public void setStartDate(final LocalDate startDate) {
        this.startDate = startDate;
    }
    public void setEndDate(final LocalDate endDate) {
        this.endDate = endDate;
    }
    public void setAmountCharged(final BigDecimal amountCharged) {
        this.amountCharged = amountCharged;
    }
    public void setStatus(final SubscriptionStatus status) {
        this.status = status;
    }
    public void setPaymentStatus(final PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
    public Subscription() {
    }
    public Subscription(final Long id, final User student, final MonthlyPlan plan, final Mess mess, final LocalDate startDate, final LocalDate endDate, final BigDecimal amountCharged, final SubscriptionStatus status, final PaymentStatus paymentStatus) {
        this.id = id;
        this.student = student;
        this.plan = plan;
        this.mess = mess;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amountCharged = amountCharged;
        this.status = status;
        this.paymentStatus = paymentStatus;
    }
}
