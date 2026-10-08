package com.messmanagement.dto;

import java.math.BigDecimal;

public class PaymentOrderResponse {
    private Long localPaymentId;
    private String razorpayOrderId;
    private BigDecimal amount;
    private String currency;
    public Long getLocalPaymentId() {
        return this.localPaymentId;
    }
    public String getRazorpayOrderId() {
        return this.razorpayOrderId;
    }
    public BigDecimal getAmount() {
        return this.amount;
    }
    public String getCurrency() {
        return this.currency;
    }
    public void setLocalPaymentId(final Long localPaymentId) {
        this.localPaymentId = localPaymentId;
    }
    public void setRazorpayOrderId(final String razorpayOrderId) {
        this.razorpayOrderId = razorpayOrderId;
    }
    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }
    public void setCurrency(final String currency) {
        this.currency = currency;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof PaymentOrderResponse)) return false;
        final PaymentOrderResponse other = (PaymentOrderResponse) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$localPaymentId = this.getLocalPaymentId();
        final java.lang.Object other$localPaymentId = other.getLocalPaymentId();
        if (this$localPaymentId == null ? other$localPaymentId != null : !this$localPaymentId.equals(other$localPaymentId)) return false;
        final java.lang.Object this$razorpayOrderId = this.getRazorpayOrderId();
        final java.lang.Object other$razorpayOrderId = other.getRazorpayOrderId();
        if (this$razorpayOrderId == null ? other$razorpayOrderId != null : !this$razorpayOrderId.equals(other$razorpayOrderId)) return false;
        final java.lang.Object this$amount = this.getAmount();
        final java.lang.Object other$amount = other.getAmount();
        if (this$amount == null ? other$amount != null : !this$amount.equals(other$amount)) return false;
        final java.lang.Object this$currency = this.getCurrency();
        final java.lang.Object other$currency = other.getCurrency();
        if (this$currency == null ? other$currency != null : !this$currency.equals(other$currency)) return false;
        return true;
    }
    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof PaymentOrderResponse;
    }

    @java.lang.Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $localPaymentId = this.getLocalPaymentId();
        result = result * PRIME + ($localPaymentId == null ? 43 : $localPaymentId.hashCode());
        final java.lang.Object $razorpayOrderId = this.getRazorpayOrderId();
        result = result * PRIME + ($razorpayOrderId == null ? 43 : $razorpayOrderId.hashCode());
        final java.lang.Object $amount = this.getAmount();
        result = result * PRIME + ($amount == null ? 43 : $amount.hashCode());
        final java.lang.Object $currency = this.getCurrency();
        result = result * PRIME + ($currency == null ? 43 : $currency.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "PaymentOrderResponse(localPaymentId=" + this.getLocalPaymentId() + ", razorpayOrderId=" + this.getRazorpayOrderId() + ", amount=" + this.getAmount() + ", currency=" + this.getCurrency() + ")";
    }
    public PaymentOrderResponse(final Long localPaymentId, final String razorpayOrderId, final BigDecimal amount, final String currency) {
        this.localPaymentId = localPaymentId;
        this.razorpayOrderId = razorpayOrderId;
        this.amount = amount;
        this.currency = currency;
    }
}
