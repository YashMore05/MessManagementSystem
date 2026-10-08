package com.messmanagement.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class MonthlyPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private Mess mess;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private Integer mealsIncluded;
    @Column(nullable = false)
    private Integer seatLimit;
    private boolean active;

    @PrePersist
    public void prePersist() {
        active = true;
    }
    public static class MonthlyPlanBuilder {
        private Long id;
        private Mess mess;
        private String name;
        private BigDecimal price;
        private Integer mealsIncluded;
        private Integer seatLimit;
        private boolean active;
        MonthlyPlanBuilder() {
        }

        
        public MonthlyPlan.MonthlyPlanBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        
        public MonthlyPlan.MonthlyPlanBuilder mess(final Mess mess) {
            this.mess = mess;
            return this;
        }

        
        public MonthlyPlan.MonthlyPlanBuilder name(final String name) {
            this.name = name;
            return this;
        }

        
        public MonthlyPlan.MonthlyPlanBuilder price(final BigDecimal price) {
            this.price = price;
            return this;
        }

        
        public MonthlyPlan.MonthlyPlanBuilder mealsIncluded(final Integer mealsIncluded) {
            this.mealsIncluded = mealsIncluded;
            return this;
        }

        
        public MonthlyPlan.MonthlyPlanBuilder seatLimit(final Integer seatLimit) {
            this.seatLimit = seatLimit;
            return this;
        }

        
        public MonthlyPlan.MonthlyPlanBuilder active(final boolean active) {
            this.active = active;
            return this;
        }
        public MonthlyPlan build() {
            return new MonthlyPlan(this.id, this.mess, this.name, this.price, this.mealsIncluded, this.seatLimit, this.active);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "MonthlyPlan.MonthlyPlanBuilder(id=" + this.id + ", mess=" + this.mess + ", name=" + this.name + ", price=" + this.price + ", mealsIncluded=" + this.mealsIncluded + ", seatLimit=" + this.seatLimit + ", active=" + this.active + ")";
        }
    }
    public static MonthlyPlan.MonthlyPlanBuilder builder() {
        return new MonthlyPlan.MonthlyPlanBuilder();
    }
    public Long getId() {
        return this.id;
    }
    public Mess getMess() {
        return this.mess;
    }
    public String getName() {
        return this.name;
    }
    public BigDecimal getPrice() {
        return this.price;
    }
    public Integer getMealsIncluded() {
        return this.mealsIncluded;
    }
    public Integer getSeatLimit() {
        return this.seatLimit;
    }
    public boolean isActive() {
        return this.active;
    }
    public void setId(final Long id) {
        this.id = id;
    }
    public void setMess(final Mess mess) {
        this.mess = mess;
    }
    public void setName(final String name) {
        this.name = name;
    }
    public void setPrice(final BigDecimal price) {
        this.price = price;
    }
    public void setMealsIncluded(final Integer mealsIncluded) {
        this.mealsIncluded = mealsIncluded;
    }
    public void setSeatLimit(final Integer seatLimit) {
        this.seatLimit = seatLimit;
    }
    public void setActive(final boolean active) {
        this.active = active;
    }
    public MonthlyPlan() {
    }
    public MonthlyPlan(final Long id, final Mess mess, final String name, final BigDecimal price, final Integer mealsIncluded, final Integer seatLimit, final boolean active) {
        this.id = id;
        this.mess = mess;
        this.name = name;
        this.price = price;
        this.mealsIncluded = mealsIncluded;
        this.seatLimit = seatLimit;
        this.active = active;
    }
}
