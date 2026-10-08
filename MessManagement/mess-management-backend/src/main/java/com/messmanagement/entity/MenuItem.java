package com.messmanagement.entity;

import com.messmanagement.enums.MealType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private Mess mess;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MealType mealType;
    @Column(nullable = false)
    private LocalDate menuDate;
    @Column(nullable = false, length = 1000)
    private String items;
    private BigDecimal price;
    private String dishPhotoPath;
    public static class MenuItemBuilder {
        private Long id;
        private Mess mess;
        private MealType mealType;
        private LocalDate menuDate;
        private String items;
        private BigDecimal price;
        private String dishPhotoPath;
        MenuItemBuilder() {
        }

        
        public MenuItem.MenuItemBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        
        public MenuItem.MenuItemBuilder mess(final Mess mess) {
            this.mess = mess;
            return this;
        }

        
        public MenuItem.MenuItemBuilder mealType(final MealType mealType) {
            this.mealType = mealType;
            return this;
        }

        
        public MenuItem.MenuItemBuilder menuDate(final LocalDate menuDate) {
            this.menuDate = menuDate;
            return this;
        }

        
        public MenuItem.MenuItemBuilder items(final String items) {
            this.items = items;
            return this;
        }

        
        public MenuItem.MenuItemBuilder price(final BigDecimal price) {
            this.price = price;
            return this;
        }
        public MenuItem.MenuItemBuilder dishPhotoPath(final String dishPhotoPath) {
            this.dishPhotoPath = dishPhotoPath;
            return this;
        }
        public MenuItem build() {
            return new MenuItem(this.id, this.mess, this.mealType, this.menuDate, this.items, this.price, this.dishPhotoPath);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "MenuItem.MenuItemBuilder(id=" + this.id + ", mess=" + this.mess + ", mealType=" + this.mealType + ", menuDate=" + this.menuDate + ", items=" + this.items + ", price=" + this.price + ", dishPhotoPath=" + this.dishPhotoPath + ")";
        }
    }
    public static MenuItem.MenuItemBuilder builder() {
        return new MenuItem.MenuItemBuilder();
    }
    public Long getId() {
        return this.id;
    }
    public Mess getMess() {
        return this.mess;
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
    public void setId(final Long id) {
        this.id = id;
    }
    public void setMess(final Mess mess) {
        this.mess = mess;
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
    public MenuItem() {
    }
    public MenuItem(final Long id, final Mess mess, final MealType mealType, final LocalDate menuDate, final String items, final BigDecimal price, final String dishPhotoPath) {
        this.id = id;
        this.mess = mess;
        this.mealType = mealType;
        this.menuDate = menuDate;
        this.items = items;
        this.price = price;
        this.dishPhotoPath = dishPhotoPath;
    }
}
