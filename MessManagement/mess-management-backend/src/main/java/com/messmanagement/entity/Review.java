package com.messmanagement.entity;

import com.messmanagement.enums.MealType;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private User student;
    @ManyToOne(optional = false)
    private Mess mess;
    @Enumerated(EnumType.STRING)
    private MealType mealType;
    private LocalDate mealDate;
    private Integer rating;
    private String comment;
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
    public static class ReviewBuilder {
        private Long id;
        private User student;
        private Mess mess;
        private MealType mealType;
        private LocalDate mealDate;
        private Integer rating;
        private String comment;
        private LocalDateTime createdAt;
        ReviewBuilder() {
        }

        
        public Review.ReviewBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        
        public Review.ReviewBuilder student(final User student) {
            this.student = student;
            return this;
        }

        
        public Review.ReviewBuilder mess(final Mess mess) {
            this.mess = mess;
            return this;
        }

        
        public Review.ReviewBuilder mealType(final MealType mealType) {
            this.mealType = mealType;
            return this;
        }

        
        public Review.ReviewBuilder mealDate(final LocalDate mealDate) {
            this.mealDate = mealDate;
            return this;
        }

        
        public Review.ReviewBuilder rating(final Integer rating) {
            this.rating = rating;
            return this;
        }

        
        public Review.ReviewBuilder comment(final String comment) {
            this.comment = comment;
            return this;
        }

        
        public Review.ReviewBuilder createdAt(final LocalDateTime createdAt) {
            this.createdAt = createdAt;
            return this;
        }
        public Review build() {
            return new Review(this.id, this.student, this.mess, this.mealType, this.mealDate, this.rating, this.comment, this.createdAt);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "Review.ReviewBuilder(id=" + this.id + ", student=" + this.student + ", mess=" + this.mess + ", mealType=" + this.mealType + ", mealDate=" + this.mealDate + ", rating=" + this.rating + ", comment=" + this.comment + ", createdAt=" + this.createdAt + ")";
        }
    }
    public static Review.ReviewBuilder builder() {
        return new Review.ReviewBuilder();
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
    public MealType getMealType() {
        return this.mealType;
    }
    public LocalDate getMealDate() {
        return this.mealDate;
    }
    public Integer getRating() {
        return this.rating;
    }
    public String getComment() {
        return this.comment;
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
    public void setMealType(final MealType mealType) {
        this.mealType = mealType;
    }
    public void setMealDate(final LocalDate mealDate) {
        this.mealDate = mealDate;
    }
    public void setRating(final Integer rating) {
        this.rating = rating;
    }
    public void setComment(final String comment) {
        this.comment = comment;
    }
    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public Review() {
    }
    public Review(final Long id, final User student, final Mess mess, final MealType mealType, final LocalDate mealDate, final Integer rating, final String comment, final LocalDateTime createdAt) {
        this.id = id;
        this.student = student;
        this.mess = mess;
        this.mealType = mealType;
        this.mealDate = mealDate;
        this.rating = rating;
        this.comment = comment;
        this.createdAt = createdAt;
    }
}
