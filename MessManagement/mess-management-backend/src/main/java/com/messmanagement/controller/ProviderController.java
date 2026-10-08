package com.messmanagement.controller;

import com.messmanagement.dto.*;
import com.messmanagement.entity.*;
import com.messmanagement.service.ProviderService;
import com.messmanagement.util.CurrentUser;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/provider")
public class ProviderController {
    private final ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @PostMapping("/messes")
    public Mess saveMess(@Valid @RequestBody MessRequest request) {
        return providerService.saveMess(CurrentUser.get(), request);
    }

    @GetMapping("/messes")
    public List<Mess> myMesses() {
        return providerService.myMesses(CurrentUser.get());
    }

    @PostMapping("/menus")
    public MenuItem setMenu(@Valid @RequestBody MenuRequest request) {
        return providerService.setMenu(CurrentUser.get(), request);
    }

    @PostMapping("/plans")
    public MonthlyPlan setPlan(@Valid @RequestBody PlanRequest request) {
        return providerService.setPlan(CurrentUser.get(), request);
    }

    @GetMapping("/messes/{messId}/plans")
    public List<MonthlyPlan> getPlans(@PathVariable Long messId) {
        return providerService.getPlans(CurrentUser.get(), messId);
    }

    @GetMapping("/messes/{messId}/subscriptions")
    public List<Subscription> subscriptions(@PathVariable Long messId) {
        return providerService.subscriptions(CurrentUser.get(), messId);
    }

    @GetMapping("/messes/{messId}/reviews")
    public List<Review> reviews(@PathVariable Long messId) {
        return providerService.reviews(CurrentUser.get(), messId);
    }

    @GetMapping("/messes/{messId}/leaves")
    public List<LeaveRequest> leaves(@PathVariable Long messId) {
        return providerService.leaves(CurrentUser.get(), messId);
    }

    @PostMapping("/qr/scan")
    public Attendance scanQr(@RequestBody Map<String, String> body) {
        return providerService.scanQr(CurrentUser.get(), body.get("token"));
    }

    @GetMapping("/messes/{messId}/meal-bookings")
    public List<MealBooking> mealBookings(@PathVariable Long messId) {
        return providerService.mealBookings(CurrentUser.get(), messId);
    }

    @GetMapping("/messes/{messId}/live-counts")
    public Map<String, Object> liveCounts(@PathVariable Long messId) {
        return providerService.liveMealCounts(CurrentUser.get(), messId);
    }

    @PostMapping("/notices")
    public Notice notice(@Valid @RequestBody NoticeRequest request) {
        return providerService.notice(CurrentUser.get(), request);
    }

    @GetMapping("/messes/{messId}/notices")
    public List<Notice> notices(@PathVariable Long messId) {
        return providerService.notices(CurrentUser.get(), messId);
    }

    @PostMapping("/polls")
    public Poll createPoll(@Valid @RequestBody PollRequest request) {
        return providerService.createPoll(CurrentUser.get(), request);
    }

    @GetMapping("/messes/{messId}/polls")
    public List<Poll> polls(@PathVariable Long messId) {
        return providerService.polls(CurrentUser.get(), messId);
    }

    @GetMapping("/polls/{pollId}/results")
    public Map<String, Long> pollResults(@PathVariable Long pollId) {
        return providerService.pollResults(CurrentUser.get(), pollId);
    }

    @PatchMapping("/polls/{pollId}/publish")
    public Poll publishWinningDish(@PathVariable Long pollId) {
        return providerService.publishWinningDish(CurrentUser.get(), pollId);
    }

    @GetMapping("/messes/{messId}/dashboard")
    public Map<String, Object> dashboard(@PathVariable Long messId) {
        return providerService.dashboard(CurrentUser.get(), messId);
    }
}
