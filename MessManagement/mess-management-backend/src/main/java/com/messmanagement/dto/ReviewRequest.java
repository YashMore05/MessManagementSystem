package com.messmanagement.dto;

import com.messmanagement.enums.MealType;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class ReviewRequest {
    @NotNull
    private Long messId;
    @NotNull
    private MealType mealType;
    @NotNull
    private LocalDate mealDate;
    @Min(1)
    @Max(5)
    private Integer rating;
    private String comment;
    public ReviewRequest() {
    }
    public Long getMessId() {
        return this.messId;
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
    public void setMessId(final Long messId) {
        this.messId = messId;
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

    @java.lang.Override
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof ReviewRequest)) return false;
        final ReviewRequest other = (ReviewRequest) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$messId = this.getMessId();
        final java.lang.Object other$messId = other.getMessId();
        if (this$messId == null ? other$messId != null : !this$messId.equals(other$messId)) return false;
        final java.lang.Object this$rating = this.getRating();
        final java.lang.Object other$rating = other.getRating();
        if (this$rating == null ? other$rating != null : !this$rating.equals(other$rating)) return false;
        final java.lang.Object this$mealType = this.getMealType();
        final java.lang.Object other$mealType = other.getMealType();
        if (this$mealType == null ? other$mealType != null : !this$mealType.equals(other$mealType)) return false;
        final java.lang.Object this$mealDate = this.getMealDate();
        final java.lang.Object other$mealDate = other.getMealDate();
        if (this$mealDate == null ? other$mealDate != null : !this$mealDate.equals(other$mealDate)) return false;
        final java.lang.Object this$comment = this.getComment();
        final java.lang.Object other$comment = other.getComment();
        if (this$comment == null ? other$comment != null : !this$comment.equals(other$comment)) return false;
        return true;
    }
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof ReviewRequest;
    }

    @java.lang.Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $messId = this.getMessId();
        result = result * PRIME + ($messId == null ? 43 : $messId.hashCode());
        final java.lang.Object $rating = this.getRating();
        result = result * PRIME + ($rating == null ? 43 : $rating.hashCode());
        final java.lang.Object $mealType = this.getMealType();
        result = result * PRIME + ($mealType == null ? 43 : $mealType.hashCode());
        final java.lang.Object $mealDate = this.getMealDate();
        result = result * PRIME + ($mealDate == null ? 43 : $mealDate.hashCode());
        final java.lang.Object $comment = this.getComment();
        result = result * PRIME + ($comment == null ? 43 : $comment.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "ReviewRequest(messId=" + this.getMessId() + ", mealType=" + this.getMealType() + ", mealDate=" + this.getMealDate() + ", rating=" + this.getRating() + ", comment=" + this.getComment() + ")";
    }
}
