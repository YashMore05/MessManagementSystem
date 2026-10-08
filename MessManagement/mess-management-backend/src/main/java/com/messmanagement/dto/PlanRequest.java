package com.messmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class PlanRequest {
    @NotNull
    private Long messId;
    @NotBlank
    private String name;
    @NotNull
    private BigDecimal price;
    @NotNull
    private Integer mealsIncluded;
    @NotNull
    private Integer seatLimit;
    public PlanRequest() {
    }
    public Long getMessId() {
        return this.messId;
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
    public void setMessId(final Long messId) {
        this.messId = messId;
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

    @java.lang.Override
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof PlanRequest)) return false;
        final PlanRequest other = (PlanRequest) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$messId = this.getMessId();
        final java.lang.Object other$messId = other.getMessId();
        if (this$messId == null ? other$messId != null : !this$messId.equals(other$messId)) return false;
        final java.lang.Object this$mealsIncluded = this.getMealsIncluded();
        final java.lang.Object other$mealsIncluded = other.getMealsIncluded();
        if (this$mealsIncluded == null ? other$mealsIncluded != null : !this$mealsIncluded.equals(other$mealsIncluded)) return false;
        final java.lang.Object this$seatLimit = this.getSeatLimit();
        final java.lang.Object other$seatLimit = other.getSeatLimit();
        if (this$seatLimit == null ? other$seatLimit != null : !this$seatLimit.equals(other$seatLimit)) return false;
        final java.lang.Object this$name = this.getName();
        final java.lang.Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final java.lang.Object this$price = this.getPrice();
        final java.lang.Object other$price = other.getPrice();
        if (this$price == null ? other$price != null : !this$price.equals(other$price)) return false;
        return true;
    }
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof PlanRequest;
    }

    @java.lang.Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $messId = this.getMessId();
        result = result * PRIME + ($messId == null ? 43 : $messId.hashCode());
        final java.lang.Object $mealsIncluded = this.getMealsIncluded();
        result = result * PRIME + ($mealsIncluded == null ? 43 : $mealsIncluded.hashCode());
        final java.lang.Object $seatLimit = this.getSeatLimit();
        result = result * PRIME + ($seatLimit == null ? 43 : $seatLimit.hashCode());
        final java.lang.Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final java.lang.Object $price = this.getPrice();
        result = result * PRIME + ($price == null ? 43 : $price.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "PlanRequest(messId=" + this.getMessId() + ", name=" + this.getName() + ", price=" + this.getPrice() + ", mealsIncluded=" + this.getMealsIncluded() + ", seatLimit=" + this.getSeatLimit() + ")";
    }
}
