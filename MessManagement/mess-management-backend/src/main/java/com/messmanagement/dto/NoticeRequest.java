package com.messmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class NoticeRequest {
    @NotNull
    private Long messId;
    @NotBlank
    private String title;
    @NotBlank
    private String message;
    @NotNull
    private LocalDate expiryDate;
    public NoticeRequest() {
    }
    public Long getMessId() {
        return this.messId;
    }
    public String getTitle() {
        return this.title;
    }
    public String getMessage() {
        return this.message;
    }
    public LocalDate getExpiryDate() {
        return this.expiryDate;
    }
    public void setMessId(final Long messId) {
        this.messId = messId;
    }
    public void setTitle(final String title) {
        this.title = title;
    }
    public void setMessage(final String message) {
        this.message = message;
    }
    public void setExpiryDate(final LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof NoticeRequest)) return false;
        final NoticeRequest other = (NoticeRequest) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$messId = this.getMessId();
        final java.lang.Object other$messId = other.getMessId();
        if (this$messId == null ? other$messId != null : !this$messId.equals(other$messId)) return false;
        final java.lang.Object this$title = this.getTitle();
        final java.lang.Object other$title = other.getTitle();
        if (this$title == null ? other$title != null : !this$title.equals(other$title)) return false;
        final java.lang.Object this$message = this.getMessage();
        final java.lang.Object other$message = other.getMessage();
        if (this$message == null ? other$message != null : !this$message.equals(other$message)) return false;
        final java.lang.Object this$expiryDate = this.getExpiryDate();
        final java.lang.Object other$expiryDate = other.getExpiryDate();
        if (this$expiryDate == null ? other$expiryDate != null : !this$expiryDate.equals(other$expiryDate)) return false;
        return true;
    }
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof NoticeRequest;
    }

    @java.lang.Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $messId = this.getMessId();
        result = result * PRIME + ($messId == null ? 43 : $messId.hashCode());
        final java.lang.Object $title = this.getTitle();
        result = result * PRIME + ($title == null ? 43 : $title.hashCode());
        final java.lang.Object $message = this.getMessage();
        result = result * PRIME + ($message == null ? 43 : $message.hashCode());
        final java.lang.Object $expiryDate = this.getExpiryDate();
        result = result * PRIME + ($expiryDate == null ? 43 : $expiryDate.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "NoticeRequest(messId=" + this.getMessId() + ", title=" + this.getTitle() + ", message=" + this.getMessage() + ", expiryDate=" + this.getExpiryDate() + ")";
    }
}
