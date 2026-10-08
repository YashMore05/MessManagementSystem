package com.messmanagement.controller;

import com.messmanagement.dto.HygieneAuditRequest;
import com.messmanagement.dto.PollRequest;
import com.messmanagement.entity.*;
import com.messmanagement.enums.ComplaintStatus;
import com.messmanagement.enums.ProviderStatus;
import com.messmanagement.repository.ComplaintRepository;
import com.messmanagement.repository.UserRepository;
import com.messmanagement.service.AdminService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;
    private final UserRepository userRepository;
    private final ComplaintRepository complaintRepository;

    public AdminController(AdminService adminService, UserRepository userRepository, ComplaintRepository complaintRepository) {
        this.adminService = adminService;
        this.userRepository = userRepository;
        this.complaintRepository = complaintRepository;
    }

    @GetMapping("/providers/pending")
    public List<MessProviderProfile> pendingProviders() {
        return adminService.pendingProviders();
    }

    @GetMapping("/messes")
    public List<Mess> messes() {
        return adminService.messes();
    }

    @PatchMapping("/providers/{profileId}/status")
    public MessProviderProfile updateProviderStatus(@PathVariable Long profileId, @RequestParam ProviderStatus status) {
        return adminService.updateProviderStatus(profileId, status);
    }

    @GetMapping("/users")
    public List<User> users() {
        return userRepository.findAll();
    }

    @PatchMapping("/users/{userId}/ban")
    public User banUser(@PathVariable Long userId, @RequestParam boolean banned) {
        return adminService.banUser(userId, banned);
    }

    @GetMapping("/complaints")
    public List<Complaint> complaints() {
        return complaintRepository.findAll();
    }

    @PatchMapping("/complaints/{complaintId}/status")
    public Complaint updateComplaint(@PathVariable Long complaintId, @RequestParam ComplaintStatus status) {
        return adminService.updateComplaint(complaintId, status);
    }

    @PostMapping("/polls")
    public Poll createPoll(@Valid @RequestBody PollRequest request) {
        return adminService.createPoll(request);
    }

    @GetMapping("/polls/{pollId}/results")
    public Map<String, Long> pollResults(@PathVariable Long pollId) {
        return adminService.pollResults(pollId);
    }

    @PatchMapping("/polls/{pollId}/publish")
    public Poll publishWinningDish(@PathVariable Long pollId) {
        return adminService.publishWinningDish(pollId);
    }

    @PostMapping("/hygiene-audits")
    public HygieneAudit audit(@Valid @RequestBody HygieneAuditRequest request) {
        return adminService.audit(request);
    }

    @GetMapping("/analytics")
    public Map<String, Object> analytics() {
        return adminService.analytics();
    }

    @GetMapping("/leaderboard")
    public List<Map<String, Object>> leaderboard() {
        return adminService.leaderboard();
    }
}
