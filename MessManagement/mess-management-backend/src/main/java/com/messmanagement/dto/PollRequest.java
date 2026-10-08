package com.messmanagement.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class PollRequest {
    @NotNull
    private Long messId;
    private String title;
    @NotNull
    private LocalDate pollDate;
    @NotNull
    private LocalDate sundayDate;
    @NotEmpty
    private List<String> options;
    public PollRequest() {
    }
    public Long getMessId() {
        return this.messId;
    }
    public String getTitle() {
        return this.title;
    }
    public LocalDate getPollDate() {
        return this.pollDate;
    }
    public LocalDate getSundayDate() {
        return this.sundayDate;
    }
    public List<String> getOptions() {
        return this.options;
    }
    public void setMessId(final Long messId) {
        this.messId = messId;
    }
    public void setTitle(final String title) {
        this.title = title;
    }
    public void setPollDate(final LocalDate pollDate) {
        this.pollDate = pollDate;
    }
    public void setSundayDate(final LocalDate sundayDate) {
        this.sundayDate = sundayDate;
    }
    public void setOptions(final List<String> options) {
        this.options = options;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof PollRequest)) return false;
        final PollRequest other = (PollRequest) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$messId = this.getMessId();
        final java.lang.Object other$messId = other.getMessId();
        if (this$messId == null ? other$messId != null : !this$messId.equals(other$messId)) return false;
        final java.lang.Object this$title = this.getTitle();
        final java.lang.Object other$title = other.getTitle();
        if (this$title == null ? other$title != null : !this$title.equals(other$title)) return false;
        final java.lang.Object this$pollDate = this.getPollDate();
        final java.lang.Object other$pollDate = other.getPollDate();
        if (this$pollDate == null ? other$pollDate != null : !this$pollDate.equals(other$pollDate)) return false;
        final java.lang.Object this$sundayDate = this.getSundayDate();
        final java.lang.Object other$sundayDate = other.getSundayDate();
        if (this$sundayDate == null ? other$sundayDate != null : !this$sundayDate.equals(other$sundayDate)) return false;
        final java.lang.Object this$options = this.getOptions();
        final java.lang.Object other$options = other.getOptions();
        if (this$options == null ? other$options != null : !this$options.equals(other$options)) return false;
        return true;
    }
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof PollRequest;
    }

    @java.lang.Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $messId = this.getMessId();
        result = result * PRIME + ($messId == null ? 43 : $messId.hashCode());
        final java.lang.Object $title = this.getTitle();
        result = result * PRIME + ($title == null ? 43 : $title.hashCode());
        final java.lang.Object $pollDate = this.getPollDate();
        result = result * PRIME + ($pollDate == null ? 43 : $pollDate.hashCode());
        final java.lang.Object $sundayDate = this.getSundayDate();
        result = result * PRIME + ($sundayDate == null ? 43 : $sundayDate.hashCode());
        final java.lang.Object $options = this.getOptions();
        result = result * PRIME + ($options == null ? 43 : $options.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "PollRequest(messId=" + this.getMessId() + ", title=" + this.getTitle() + ", pollDate=" + this.getPollDate() + ", sundayDate=" + this.getSundayDate() + ", options=" + this.getOptions() + ")";
    }
}
