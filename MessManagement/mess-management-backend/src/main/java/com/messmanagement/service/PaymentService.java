package com.messmanagement.service;

import com.messmanagement.dto.PaymentOrderResponse;
import com.messmanagement.entity.Payment;
import com.messmanagement.entity.User;
import com.messmanagement.enums.PaymentStatus;
import com.messmanagement.repository.PaymentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final com.messmanagement.repository.SubscriptionRepository subscriptionRepository;
    private final com.messmanagement.repository.MealBookingRepository mealBookingRepository;

    @Value("${razorpay.key.id:rzp_test_change_me}")
    private String razorpayKeyId;

    @Value("${razorpay.key.secret:change_me}")
    private String razorpayKeySecret;

    public PaymentService(PaymentRepository paymentRepository,
                          com.messmanagement.repository.SubscriptionRepository subscriptionRepository,
                          com.messmanagement.repository.MealBookingRepository mealBookingRepository) {
        this.paymentRepository = paymentRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.mealBookingRepository = mealBookingRepository;
    }

    public PaymentOrderResponse createOrder(User user, BigDecimal amount, String purpose) throws Exception {
        RazorpayClient client = new RazorpayClient(razorpayKeyId, razorpayKeySecret);
        int paise = amount.multiply(BigDecimal.valueOf(100)).setScale(0, RoundingMode.HALF_UP).intValueExact();

        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", paise);
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", purpose + "-" + System.currentTimeMillis());

        Order order = client.orders.create(orderRequest);
        Payment payment = paymentRepository.save(Payment.builder()
                .user(user)
                .purpose(purpose)
                .razorpayOrderId(order.get("id"))
                .amount(amount)
                .status(PaymentStatus.CREATED)
                .build());

        return new PaymentOrderResponse(payment.getId(), payment.getRazorpayOrderId(), amount, "INR");
    }

    public Payment verifyPayment(String orderId, String paymentId, String signature) throws Exception {
        JSONObject attributes = new JSONObject();
        attributes.put("razorpay_order_id", orderId);
        attributes.put("razorpay_payment_id", paymentId);
        attributes.put("razorpay_signature", signature);
        if (!Utils.verifyPaymentSignature(attributes, razorpayKeySecret)) {
            throw new IllegalArgumentException("Invalid payment signature");
        }
        Payment payment = paymentRepository.findByRazorpayOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Payment order not found"));
        payment.setRazorpayPaymentId(paymentId);
        payment.setRazorpaySignature(signature);
        payment.setStatus(PaymentStatus.PAID);
        Payment saved = paymentRepository.save(payment);

        String purpose = payment.getPurpose();
        if (purpose != null) {
            if (purpose.startsWith("SUBSCRIPTION-")) {
                Long subId = Long.parseLong(purpose.substring("SUBSCRIPTION-".length()));
                subscriptionRepository.findById(subId).ifPresent(sub -> {
                    sub.setPaymentStatus(com.messmanagement.enums.PaymentStatus.PAID);
                    subscriptionRepository.save(sub);
                });
            } else if (purpose.startsWith("MEALBOOKING-")) {
                Long bookingId = Long.parseLong(purpose.substring("MEALBOOKING-".length()));
                mealBookingRepository.findById(bookingId).ifPresent(booking -> {
                    booking.setStatus(com.messmanagement.enums.BookingStatus.PAID);
                    booking.setPaidAt(java.time.LocalDateTime.now());
                    mealBookingRepository.save(booking);
                });
            }
        }
        return saved;
    }
}
