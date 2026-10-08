package com.messmanagement.dto;

import com.messmanagement.enums.MealType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class MenuRequest {
    @NotNull
    private Long messId;
    @NotNull
    private MealType mealType;
    @NotNull
    private LocalDate menuDate;
    @NotBlank
    private String items;
    private BigDecimal price;
    private String dishPhotoPath;
    public MenuRequest() {
    }
    public Long getMessId() {
        return this.messId;
    }
    public MealType getMealType() {
        return this.mealType;
    }
    public LocalDate getMenuDate() {
        return this.menuDate;
    }
    public String getItems() {
        return this.items;
    }
    public BigDecimal getPrice() {
        return this.price;
    }
    public String getDishPhotoPath() {
        return this.dishPhotoPath;
    }
    public void setMessId(final Long messId) {
        this.messId = messId;
    }
    public void setMealType(final MealType mealType) {
        this.mealType = mealType;
    }
    public void setMenuDate(final LocalDate menuDate) {
        this.menuDate = menuDate;
    }
    public void setItems(final String items) {
        this.items = items;
    }
    public void setPrice(final BigDecimal price) {
        this.price = price;
    }
    public void setDishPhotoPath(final String dishPhotoPath) {
        this.dishPhotoPath = dishPhotoPath;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof MenuRequest)) return false;
        final MenuRequest other = (MenuRequest) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$messId = this.getMessId();
        final java.lang.Object other$messId = other.getMessId();
        if (this$messId == null ? other$messId != null : !this$messId.equals(other$messId)) return false;
        final java.lang.Object this$mealType = this.getMealType();
        final java.lang.Object other$mealType = other.getMealType();
        if (this$mealType == null ? other$mealType != null : !this$mealType.equals(other$mealType)) return false;
        final java.lang.Object this$menuDate = this.getMenuDate();
        final java.lang.Object other$menuDate = other.getMenuDate();
        if (this$menuDate == null ? other$menuDate != null : !this$menuDate.equals(other$menuDate)) return false;
        final java.lang.Object this$items = this.getItems();
        final java.lang.Object other$items = other.getItems();
        if (this$items == null ? other$items != null : !this$items.equals(other$items)) return false;
        final java.lang.Object this$dishPhotoPath = this.getDishPhotoPath();
        final java.lang.Object other$dishPhotoPath = other.getDishPhotoPath();
        if (this$dishPhotoPath == null ? other$dishPhotoPath != null : !this$dishPhotoPath.equals(other$dishPhotoPath)) return false;
        return true;
    }
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof MenuRequest;
    }

    @java.lang.Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $messId = this.getMessId();
        result = result * PRIME + ($messId == null ? 43 : $messId.hashCode());
        final java.lang.Object $mealType = this.getMealType();
        result = result * PRIME + ($mealType == null ? 43 : $mealType.hashCode());
        final java.lang.Object $menuDate = this.getMenuDate();
        result = result * PRIME + ($menuDate == null ? 43 : $menuDate.hashCode());
        final java.lang.Object $items = this.getItems();
        result = result * PRIME + ($items == null ? 43 : $items.hashCode());
        final java.lang.Object $dishPhotoPath = this.getDishPhotoPath();
        result = result * PRIME + ($dishPhotoPath == null ? 43 : $dishPhotoPath.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "MenuRequest(messId=" + this.getMessId() + ", mealType=" + this.getMealType() + ", menuDate=" + this.getMenuDate() + ", items=" + this.getItems() + ", dishPhotoPath=" + this.getDishPhotoPath() + ")";
    }
}
