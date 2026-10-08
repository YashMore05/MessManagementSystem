package com.messmanagement.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class HygieneAuditRequest {
    @NotNull
    private Long messId;
    @NotNull
    private LocalDate auditMonth;
    private Integer kitchenScore;
    private Integer staffScore;
    private Integer storageScore;
    private String redFlags;
    public HygieneAuditRequest() {
    }
    public Long getMessId() {
        return this.messId;
    }
    public LocalDate getAuditMonth() {
        return this.auditMonth;
    }
    public Integer getKitchenScore() {
        return this.kitchenScore;
    }
    public Integer getStaffScore() {
        return this.staffScore;
    }
    public Integer getStorageScore() {
        return this.storageScore;
    }
    public String getRedFlags() {
        return this.redFlags;
    }
    public void setMessId(final Long messId) {
        this.messId = messId;
    }
    public void setAuditMonth(final LocalDate auditMonth) {
        this.auditMonth = auditMonth;
    }
    public void setKitchenScore(final Integer kitchenScore) {
        this.kitchenScore = kitchenScore;
    }
    public void setStaffScore(final Integer staffScore) {
        this.staffScore = staffScore;
    }
    public void setStorageScore(final Integer storageScore) {
        this.storageScore = storageScore;
    }
    public void setRedFlags(final String redFlags) {
        this.redFlags = redFlags;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof HygieneAuditRequest)) return false;
        final HygieneAuditRequest other = (HygieneAuditRequest) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$messId = this.getMessId();
        final java.lang.Object other$messId = other.getMessId();
        if (this$messId == null ? other$messId != null : !this$messId.equals(other$messId)) return false;
        final java.lang.Object this$kitchenScore = this.getKitchenScore();
        final java.lang.Object other$kitchenScore = other.getKitchenScore();
        if (this$kitchenScore == null ? other$kitchenScore != null : !this$kitchenScore.equals(other$kitchenScore)) return false;
        final java.lang.Object this$staffScore = this.getStaffScore();
        final java.lang.Object other$staffScore = other.getStaffScore();
        if (this$staffScore == null ? other$staffScore != null : !this$staffScore.equals(other$staffScore)) return false;
        final java.lang.Object this$storageScore = this.getStorageScore();
        final java.lang.Object other$storageScore = other.getStorageScore();
        if (this$storageScore == null ? other$storageScore != null : !this$storageScore.equals(other$storageScore)) return false;
        final java.lang.Object this$auditMonth = this.getAuditMonth();
        final java.lang.Object other$auditMonth = other.getAuditMonth();
        if (this$auditMonth == null ? other$auditMonth != null : !this$auditMonth.equals(other$auditMonth)) return false;
        final java.lang.Object this$redFlags = this.getRedFlags();
        final java.lang.Object other$redFlags = other.getRedFlags();
        if (this$redFlags == null ? other$redFlags != null : !this$redFlags.equals(other$redFlags)) return false;
        return true;
    }
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof HygieneAuditRequest;
    }

    @java.lang.Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $messId = this.getMessId();
        result = result * PRIME + ($messId == null ? 43 : $messId.hashCode());
        final java.lang.Object $kitchenScore = this.getKitchenScore();
        result = result * PRIME + ($kitchenScore == null ? 43 : $kitchenScore.hashCode());
        final java.lang.Object $staffScore = this.getStaffScore();
        result = result * PRIME + ($staffScore == null ? 43 : $staffScore.hashCode());
        final java.lang.Object $storageScore = this.getStorageScore();
        result = result * PRIME + ($storageScore == null ? 43 : $storageScore.hashCode());
        final java.lang.Object $auditMonth = this.getAuditMonth();
        result = result * PRIME + ($auditMonth == null ? 43 : $auditMonth.hashCode());
        final java.lang.Object $redFlags = this.getRedFlags();
        result = result * PRIME + ($redFlags == null ? 43 : $redFlags.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "HygieneAuditRequest(messId=" + this.getMessId() + ", auditMonth=" + this.getAuditMonth() + ", kitchenScore=" + this.getKitchenScore() + ", staffScore=" + this.getStaffScore() + ", storageScore=" + this.getStorageScore() + ", redFlags=" + this.getRedFlags() + ")";
    }
}
