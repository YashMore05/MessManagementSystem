package com.messmanagement.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class LeaveRequestDto {
    @NotNull
    private Long messId;
    @NotNull
    private LocalDate fromDate;
    @NotNull
    private LocalDate toDate;
    private String reason;
    public LeaveRequestDto() {
    }
    public Long getMessId() {
        return this.messId;
    }
    public LocalDate getFromDate() {
        return this.fromDate;
    }
    public LocalDate getToDate() {
        return this.toDate;
    }
    public String getReason() {
        return this.reason;
    }
    public void setMessId(final Long messId) {
        this.messId = messId;
    }
    public void setFromDate(final LocalDate fromDate) {
        this.fromDate = fromDate;
    }
    public void setToDate(final LocalDate toDate) {
        this.toDate = toDate;
    }
    public void setReason(final String reason) {
        this.reason = reason;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof LeaveRequestDto)) return false;
        final LeaveRequestDto other = (LeaveRequestDto) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$messId = this.getMessId();
        final java.lang.Object other$messId = other.getMessId();
        if (this$messId == null ? other$messId != null : !this$messId.equals(other$messId)) return false;
        final java.lang.Object this$fromDate = this.getFromDate();
        final java.lang.Object other$fromDate = other.getFromDate();
        if (this$fromDate == null ? other$fromDate != null : !this$fromDate.equals(other$fromDate)) return false;
        final java.lang.Object this$toDate = this.getToDate();
        final java.lang.Object other$toDate = other.getToDate();
        if (this$toDate == null ? other$toDate != null : !this$toDate.equals(other$toDate)) return false;
        final java.lang.Object this$reason = this.getReason();
        final java.lang.Object other$reason = other.getReason();
        if (this$reason == null ? other$reason != null : !this$reason.equals(other$reason)) return false;
        return true;
    }
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof LeaveRequestDto;
    }

    @java.lang.Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $messId = this.getMessId();
        result = result * PRIME + ($messId == null ? 43 : $messId.hashCode());
        final java.lang.Object $fromDate = this.getFromDate();
        result = result * PRIME + ($fromDate == null ? 43 : $fromDate.hashCode());
        final java.lang.Object $toDate = this.getToDate();
        result = result * PRIME + ($toDate == null ? 43 : $toDate.hashCode());
        final java.lang.Object $reason = this.getReason();
        result = result * PRIME + ($reason == null ? 43 : $reason.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "LeaveRequestDto(messId=" + this.getMessId() + ", fromDate=" + this.getFromDate() + ", toDate=" + this.getToDate() + ", reason=" + this.getReason() + ")";
    }
}
