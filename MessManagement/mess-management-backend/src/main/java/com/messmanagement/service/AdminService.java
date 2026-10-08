package com.messmanagement.service;

import com.messmanagement.dto.HygieneAuditRequest;
import com.messmanagement.dto.PollRequest;
import com.messmanagement.entity.*;
import com.messmanagement.enums.*;
import com.messmanagement.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminService {
    private final UserRepository userRepository;
    private final MessProviderProfileRepository providerProfileRepository;
    private final MessRepository messRepository;
    private final ComplaintRepository complaintRepository;
    private final PollRepository pollRepository;
    private final PollVoteRepository pollVoteRepository;
    private final HygieneAuditRepository hygieneAuditRepository;
    private final ReviewRepository reviewRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final MealBookingRepository mealBookingRepository;
    private final NotificationRepository notificationRepository;

    public AdminService(UserRepository userRepository, MessProviderProfileRepository providerProfileRepository,
                        MessRepository messRepository, ComplaintRepository complaintRepository,
                        PollRepository pollRepository, PollVoteRepository pollVoteRepository,
                        HygieneAuditRepository hygieneAuditRepository, ReviewRepository reviewRepository,
                        SubscriptionRepository subscriptionRepository, MealBookingRepository mealBookingRepository,
                        NotificationRepository notificationRepository) {
        this.userRepository = userRepository;
        this.providerProfileRepository = providerProfileRepository;
        this.messRepository = messRepository;
        this.complaintRepository = complaintRepository;
        this.pollRepository = pollRepository;
        this.pollVoteRepository = pollVoteRepository;
        this.hygieneAuditRepository = hygieneAuditRepository;
        this.reviewRepository = reviewRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.mealBookingRepository = mealBookingRepository;
        this.notificationRepository = notificationRepository;
    }

    public List<MessProviderProfile> pendingProviders() {
        return providerProfileRepository.findByStatus(ProviderStatus.PENDING);
    }

    public List<Mess> messes() {
        return messRepository.findAll();
    }

    public MessProviderProfile updateProviderStatus(Long profileId, ProviderStatus status) {
        MessProviderProfile profile = providerProfileRepository.findById(profileId)
                .orElseThrow(() -> new IllegalArgumentException("Provider profile not found"));
        profile.setStatus(status);
        return providerProfileRepository.save(profile);
    }

    public User banUser(Long userId, boolean banned) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setBanned(banned);
        return userRepository.save(user);
    }

    public Complaint updateComplaint(Long complaintId, ComplaintStatus status) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new IllegalArgumentException("Complaint not found"));
        complaint.setStatus(status);
        if (status == ComplaintStatus.RESOLVED) {
            complaint.setResolvedAt(LocalDateTime.now());
        }
        Complaint saved = complaintRepository.save(complaint);
        notificationRepository.save(Notification.builder()
                .user(saved.getStudent())
                .title("Complaint " + status.name().toLowerCase())
                .message(saved.getTitle() + " is now " + status.name())
                .readFlag(false)
                .build());
        return saved;
    }

    public Poll createPoll(PollRequest request) {
        Mess mess = messRepository.findById(request.getMessId()).orElseThrow(() -> new IllegalArgumentException("Mess not found"));
        Poll poll = Poll.builder()
                .mess(mess)
                .title(request.getTitle())
                .pollDate(request.getPollDate())
                .sundayDate(request.getSundayDate())
                .published(false)
                .build();
        request.getOptions().forEach(option -> poll.getOptions().add(PollOption.builder().poll(poll).dishName(option).build()));
        return pollRepository.save(poll);
    }

    public Map<String, Long> pollResults(Long pollId) {
        Poll poll = pollRepository.findById(pollId).orElseThrow(() -> new IllegalArgumentException("Poll not found"));
        Map<String, Long> results = new LinkedHashMap<>();
        pollVoteRepository.resultsForPoll(poll).forEach(row -> results.put((String) row[0], (Long) row[1]));
        return results;
    }

    public Poll publishWinningDish(Long pollId) {
        Poll poll = pollRepository.findById(pollId).orElseThrow(() -> new IllegalArgumentException("Poll not found"));
        String winningDish = pollVoteRepository.resultsForPoll(poll).stream()
                .findFirst()
                .map(row -> (String) row[0])
                .orElse("No votes");
        poll.setWinningDish(winningDish);
        poll.setPublished(true);
        return pollRepository.save(poll);
    }

    public HygieneAudit audit(HygieneAuditRequest request) {
        Mess mess = messRepository.findById(request.getMessId()).orElseThrow(() -> new IllegalArgumentException("Mess not found"));
        int kitchen = safeScore(request.getKitchenScore());
        int staff = safeScore(request.getStaffScore());
        int storage = safeScore(request.getStorageScore());
        return hygieneAuditRepository.save(HygieneAudit.builder()
                .mess(mess)
                .auditMonth(request.getAuditMonth())
                .kitchenScore(kitchen)
                .staffScore(staff)
                .storageScore(storage)
                .overallScore((kitchen + staff + storage) / 3)
                .redFlags(request.getRedFlags())
                .build());
    }

    public Map<String, Object> analytics() {
        Map<String, Object> analytics = new LinkedHashMap<>();
        analytics.put("totalStudents", userRepository.countByRole(Role.ROLE_STUDENT));
        analytics.put("totalProviders", userRepository.countByRole(Role.ROLE_PROVIDER));
        analytics.put("activeMesses", messRepository.countByActiveTrue());
        analytics.put("activeSubscriptions", subscriptionRepository.countByStatus(SubscriptionStatus.ACTIVE));
        analytics.put("openComplaints", complaintRepository.findByStatus(ComplaintStatus.OPEN).size());
        analytics.put("todayBookings", messRepository.findByActiveTrue().stream()
                .mapToLong(mess -> mealBookingRepository.findByMessAndMealDate(mess, java.time.LocalDate.now()).size())
                .sum());
        analytics.put("todayConsumed", messRepository.findByActiveTrue().stream()
                .mapToLong(mess -> mealBookingRepository.findByMessAndMealDate(mess, java.time.LocalDate.now()).stream()
                        .filter(booking -> booking.getStatus() == BookingStatus.CONSUMED)
                        .count())
                .sum());
        return analytics;
    }

    public List<Map<String, Object>> leaderboard() {
        return messRepository.findByActiveTrue().stream()
                .map(mess -> {
                    double rating = reviewRepository.averageRatingForMess(mess);
                    double hygiene = hygieneAuditRepository.findByMess(mess).stream()
                            .map(HygieneAudit::getOverallScore)
                            .filter(score -> score != null)
                            .mapToInt(Integer::intValue)
                            .average()
                            .orElse(0);
                    double score = (rating * 20 * 0.5) + (hygiene * 0.4) + 10;
                    Map<String, Object> row = new LinkedHashMap<>();
                    row.put("messId", mess.getId());
                    row.put("name", mess.getName());
                    row.put("rating", rating);
                    row.put("hygieneScore", hygiene);
                    row.put("compositeScore", score);
                    return row;
                })
                .sorted(Comparator.comparing(row -> -((Double) row.get("compositeScore"))))
                .toList();
    }

    private int safeScore(Integer score) {
        if (score == null) {
            return 0;
        }
        return Math.max(0, Math.min(100, score));
    }
}
