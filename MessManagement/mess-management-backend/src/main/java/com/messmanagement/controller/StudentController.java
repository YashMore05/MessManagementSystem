package com.messmanagement.controller;

import com.messmanagement.dto.*;
import com.messmanagement.entity.*;
import com.messmanagement.enums.MealType;
import com.messmanagement.enums.MessType;
import com.messmanagement.service.PaymentService;
import com.messmanagement.service.StudentService;
import com.messmanagement.util.CurrentUser;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    private final StudentService studentService;
    private final PaymentService paymentService;

    public StudentController(StudentService studentService, PaymentService paymentService) {
        this.studentService = studentService;
        this.paymentService = paymentService;
    }

    @GetMapping("/messes")
    public List<Mess> searchMesses(
            @RequestParam(required = false) String area,
            @RequestParam(required = false) MessType messType,
            @RequestParam(required = false) Double lat,
            @RequestParam(required = false) Double lng,
            @RequestParam(required = false) Double radiusKm
    ) {
        return studentService.searchMess(area, messType, lat, lng, radiusKm);
    }

    @GetMapping("/messes/{messId}/today-menu")
    public List<MenuItem> todayMenu(@PathVariable Long messId) {
        return studentService.todayMenu(messId);
    }

    @PostMapping("/menu/{menuItemId}/book")
    public MealBooking bookMeal(@PathVariable Long menuItemId) {
        return studentService.bookMeal(CurrentUser.get(), menuItemId);
    }

    @PatchMapping("/meal-bookings/{bookingId}/demo-pay")
    public MealBooking payMealBooking(@PathVariable Long bookingId) {
        return studentService.payMealBooking(CurrentUser.get(), bookingId);
    }

    @GetMapping("/meal-bookings")
    public List<MealBooking> mealBookings() {
        return studentService.mealBookings(CurrentUser.get());
    }

    @GetMapping("/messes/{messId}/plans")
    public List<MonthlyPlan> plans(@PathVariable Long messId) {
        return studentService.plans(messId);
    }

    @PostMapping("/plans/{planId}/subscribe")
    public Subscription subscribe(@PathVariable Long planId) {
        return studentService.subscribe(CurrentUser.get(), planId);
    }

    @GetMapping("/subscriptions")
    public List<Subscription> subscriptions() {
        return studentService.subscriptions(CurrentUser.get());
    }

    @PatchMapping("/subscriptions/{subscriptionId}/demo-pay")
    public Subscription demoPay(@PathVariable Long subscriptionId) {
        return studentService.markSubscriptionPaid(CurrentUser.get(), subscriptionId);
    }

    @PostMapping("/payments/order")
    public PaymentOrderResponse createPaymentOrder(@RequestBody Map<String, String> body) throws Exception {
        return paymentService.createOrder(CurrentUser.get(), new BigDecimal(body.get("amount")), body.getOrDefault("purpose", "PAYMENT"));
    }

    @PostMapping("/payments/verify")
    public Payment verifyPayment(@RequestBody Map<String, String> body) throws Exception {
        return paymentService.verifyPayment(body.get("orderId"), body.get("paymentId"), body.get("signature"));
    }

    @PostMapping("/reviews")
    public Review review(@Valid @RequestBody ReviewRequest request) {
        return studentService.review(CurrentUser.get(), request);
    }

    @PostMapping("/complaints")
    public Complaint complaint(@Valid @RequestBody ComplaintRequest request) {
        return studentService.complaint(CurrentUser.get(), request);
    }

    @GetMapping("/complaints")
    public List<Complaint> complaints() {
        return studentService.complaints(CurrentUser.get());
    }

    @GetMapping("/messes/{messId}/notices")
    public List<Notice> notices(@PathVariable Long messId) {
        return studentService.notices(messId);
    }

    @PostMapping("/notices/{noticeId}/read")
    public NoticeRead markNoticeRead(@PathVariable Long noticeId) {
        return studentService.markNoticeRead(CurrentUser.get(), noticeId);
    }

    @GetMapping("/messes/{messId}/polls")
    public List<Poll> polls(@PathVariable Long messId) {
        return studentService.polls(messId);
    }

    @GetMapping("/qr")
    public Map<String, String> qr(@RequestParam Long messId, @RequestParam MealType mealType) {
        return Map.of("token", studentService.qr(CurrentUser.get(), messId, mealType));
    }

    @PostMapping("/polls/{pollId}/vote/{optionId}")
    public PollVote vote(@PathVariable Long pollId, @PathVariable Long optionId) {
        return studentService.vote(CurrentUser.get(), pollId, optionId);
    }

    @PostMapping("/leave")
    public LeaveRequest leave(@Valid @RequestBody LeaveRequestDto request) {
        return studentService.markLeave(CurrentUser.get(), request);
    }

    @GetMapping("/notifications")
    public List<Notification> notifications() {
        return studentService.notifications(CurrentUser.get());
    }
}
