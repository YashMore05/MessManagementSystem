package com.messmanagement.service;

import com.messmanagement.dto.*;
import com.messmanagement.entity.*;
import com.messmanagement.enums.BookingStatus;
import com.messmanagement.enums.ComplaintStatus;
import com.messmanagement.enums.PaymentStatus;
import com.messmanagement.enums.SubscriptionStatus;
import com.messmanagement.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProviderService {
    private final MessRepository messRepository;
    private final MenuItemRepository menuItemRepository;
    private final MonthlyPlanRepository monthlyPlanRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final ReviewRepository reviewRepository;
    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;
    private final QrTokenService qrTokenService;
    private final NoticeRepository noticeRepository;
    private final ComplaintRepository complaintRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final PollRepository pollRepository;
    private final PollVoteRepository pollVoteRepository;
    private final MealBookingRepository mealBookingRepository;
    private final NotificationRepository notificationRepository;

    public ProviderService(MessRepository messRepository, MenuItemRepository menuItemRepository,
                           MonthlyPlanRepository monthlyPlanRepository, SubscriptionRepository subscriptionRepository,
                           ReviewRepository reviewRepository, AttendanceRepository attendanceRepository,
                           UserRepository userRepository, QrTokenService qrTokenService,
                           NoticeRepository noticeRepository, ComplaintRepository complaintRepository,
                           LeaveRequestRepository leaveRequestRepository, PollRepository pollRepository,
                           PollVoteRepository pollVoteRepository, MealBookingRepository mealBookingRepository,
                           NotificationRepository notificationRepository) {
        this.messRepository = messRepository;
        this.menuItemRepository = menuItemRepository;
        this.monthlyPlanRepository = monthlyPlanRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.reviewRepository = reviewRepository;
        this.attendanceRepository = attendanceRepository;
        this.userRepository = userRepository;
        this.qrTokenService = qrTokenService;
        this.noticeRepository = noticeRepository;
        this.complaintRepository = complaintRepository;
        this.leaveRequestRepository = leaveRequestRepository;
        this.pollRepository = pollRepository;
        this.pollVoteRepository = pollVoteRepository;
        this.mealBookingRepository = mealBookingRepository;
        this.notificationRepository = notificationRepository;
    }

    public Mess saveMess(User provider, MessRequest request) {
        Mess mess;
        if (request.getId() != null) {
            mess = messRepository.findById(request.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Mess not found"));
            if (!mess.getProvider().getId().equals(provider.getId())) {
                throw new IllegalArgumentException("You do not own this mess");
            }
            mess.setName(request.getName());
            mess.setAddress(request.getAddress());
            mess.setArea(request.getArea());
            mess.setLatitude(request.getLatitude());
            mess.setLongitude(request.getLongitude());
            mess.setCapacity(request.getCapacity());
            mess.setMessType(request.getMessType());
            mess.setPhotoPaths(request.getPhotoPaths());
        } else {
            mess = Mess.builder()
                    .provider(provider)
                    .name(request.getName())
                    .address(request.getAddress())
                    .area(request.getArea())
                    .latitude(request.getLatitude())
                    .longitude(request.getLongitude())
                    .capacity(request.getCapacity())
                    .messType(request.getMessType())
                    .photoPaths(request.getPhotoPaths())
                    .active(true)
                    .build();
        }
        return messRepository.save(mess);
    }

    public List<Mess> myMesses(User provider) {
        List<Mess> messes = messRepository.findByProvider(provider);
        for (Mess m : messes) {
            List<MonthlyPlan> plans = monthlyPlanRepository.findByMessAndActiveTrue(m);
            if (!plans.isEmpty()) {
                java.math.BigDecimal minPrice = plans.stream()
                        .map(MonthlyPlan::getPrice)
                        .min(java.math.BigDecimal::compareTo)
                        .orElse(java.math.BigDecimal.ZERO);
                m.setStartingPrice(minPrice);
            } else {
                m.setStartingPrice(java.math.BigDecimal.ZERO);
            }
        }
        return messes;
    }

    public List<MonthlyPlan> getPlans(User provider, Long messId) {
        Mess mess = ownedMess(provider, messId);
        return monthlyPlanRepository.findByMessAndActiveTrue(mess);
    }

    public MenuItem setMenu(User provider, MenuRequest request) {
        Mess mess = ownedMess(provider, request.getMessId());
        return menuItemRepository.save(MenuItem.builder()
                .mess(mess)
                .mealType(request.getMealType())
                .menuDate(request.getMenuDate())
                .items(request.getItems())
                .price(request.getPrice())
                .dishPhotoPath(request.getDishPhotoPath())
                .build());
    }

    public MonthlyPlan setPlan(User provider, PlanRequest request) {
        Mess mess = ownedMess(provider, request.getMessId());
        return monthlyPlanRepository.save(MonthlyPlan.builder()
                .mess(mess)
                .name(request.getName())
                .price(request.getPrice())
                .mealsIncluded(request.getMealsIncluded())
                .seatLimit(request.getSeatLimit())
                .active(true)
                .build());
    }

    public List<Subscription> subscriptions(User provider, Long messId) {
        return subscriptionRepository.findByMess(ownedMess(provider, messId));
    }

    public List<Review> reviews(User provider, Long messId) {
        return reviewRepository.findByMess(ownedMess(provider, messId));
    }

    public List<LeaveRequest> leaves(User provider, Long messId) {
        return leaveRequestRepository.findByMess(ownedMess(provider, messId));
    }

    public Attendance scanQr(User provider, String qrToken) {
        try {
            return scanBookingQr(provider, qrToken);
        } catch (IllegalArgumentException ignored) {
            QrTokenService.ParsedQr parsedQr = qrTokenService.parseAndValidate(qrToken);
            Mess mess = ownedMess(provider, parsedQr.messId());
            User student = userRepository.findById(parsedQr.studentId())
                    .orElseThrow(() -> new IllegalArgumentException("Student not found"));
            return attendanceRepository.findByStudentAndMessAndMealDateAndMealType(
                            student, mess, parsedQr.date(), parsedQr.mealType())
                    .orElseGet(() -> attendanceRepository.save(Attendance.builder()
                            .student(student)
                            .mess(mess)
                            .mealDate(parsedQr.date())
                            .mealType(parsedQr.mealType())
                            .scannedAt(LocalDateTime.now())
                            .build()));
        }
    }

    private Attendance scanBookingQr(User provider, String qrToken) {
        QrTokenService.ParsedBookingQr parsedQr = qrTokenService.parseBookingAndValidate(qrToken);
        Mess mess = ownedMess(provider, parsedQr.messId());
        MealBooking booking = mealBookingRepository.findById(parsedQr.bookingId())
                .orElseThrow(() -> new IllegalArgumentException("Meal booking not found"));
        if (!booking.getMess().getId().equals(mess.getId())) {
            throw new IllegalArgumentException("Booking does not belong to this mess");
        }
        if (booking.getStatus() != BookingStatus.PAID) {
            throw new IllegalArgumentException("Meal is not paid yet");
        }
        User student = userRepository.findById(parsedQr.studentId())
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
        Attendance attendance = attendanceRepository.findByStudentAndMessAndMealDateAndMealType(
                        student, mess, parsedQr.date(), parsedQr.mealType())
                .orElseGet(() -> attendanceRepository.save(Attendance.builder()
                        .student(student)
                        .mess(mess)
                        .mealDate(parsedQr.date())
                        .mealType(parsedQr.mealType())
                        .scannedAt(LocalDateTime.now())
                        .build()));
        booking.setStatus(BookingStatus.CONSUMED);
        booking.setConsumedAt(LocalDateTime.now());
        mealBookingRepository.save(booking);
        notify(student, "Meal consumed", booking.getMealType() + " marked consumed at " + mess.getName() + ".");
        return attendance;
    }

    public List<MealBooking> mealBookings(User provider, Long messId) {
        return mealBookingRepository.findByMessAndMealDate(ownedMess(provider, messId), LocalDate.now());
    }

    public Map<String, Object> liveMealCounts(User provider, Long messId) {
        Mess mess = ownedMess(provider, messId);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("messId", mess.getId());
        result.put("date", LocalDate.now());
        for (com.messmanagement.enums.MealType mealType : com.messmanagement.enums.MealType.values()) {
            Map<String, Long> row = new LinkedHashMap<>();
            row.put("booked", mealBookingRepository.countByMessAndMealDateAndMealType(mess, LocalDate.now(), mealType));
            row.put("paid", mealBookingRepository.countByMessAndMealDateAndMealTypeAndStatus(mess, LocalDate.now(), mealType, BookingStatus.PAID));
            row.put("consumed", mealBookingRepository.countByMessAndMealDateAndMealTypeAndStatus(mess, LocalDate.now(), mealType, BookingStatus.CONSUMED));
            result.put(mealType.name(), row);
        }
        return result;
    }

    public Notice notice(User provider, NoticeRequest request) {
        Notice notice = noticeRepository.save(Notice.builder()
                .mess(ownedMess(provider, request.getMessId()))
                .title(request.getTitle())
                .message(request.getMessage())
                .expiryDate(request.getExpiryDate())
                .readCount(0)
                .build());
        subscriptionRepository.findByMessAndStatus(notice.getMess(), SubscriptionStatus.ACTIVE)
                .forEach(subscription -> notify(subscription.getStudent(), "New notice from " + notice.getMess().getName(), notice.getTitle()));
        return notice;
    }

    public List<Notice> notices(User provider, Long messId) {
        return noticeRepository.findByMessAndExpiryDateGreaterThanEqual(ownedMess(provider, messId), LocalDate.now());
    }

    public Poll createPoll(User provider, PollRequest request) {
        Mess mess = ownedMess(provider, request.getMessId());
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

    public List<Poll> polls(User provider, Long messId) {
        return pollRepository.findByMessAndPollDate(ownedMess(provider, messId), LocalDate.now());
    }

    public Map<String, Long> pollResults(User provider, Long pollId) {
        Poll poll = pollRepository.findById(pollId).orElseThrow(() -> new IllegalArgumentException("Poll not found"));
        ownedMess(provider, poll.getMess().getId());
        Map<String, Long> results = new LinkedHashMap<>();
        pollVoteRepository.resultsForPoll(poll).forEach(row -> results.put((String) row[0], (Long) row[1]));
        return results;
    }

    public Poll publishWinningDish(User provider, Long pollId) {
        Poll poll = pollRepository.findById(pollId).orElseThrow(() -> new IllegalArgumentException("Poll not found"));
        ownedMess(provider, poll.getMess().getId());
        String winningDish = pollVoteRepository.resultsForPoll(poll).stream()
                .findFirst()
                .map(row -> (String) row[0])
                .orElse("No votes");
        poll.setWinningDish(winningDish);
        poll.setPublished(true);
        Poll saved = pollRepository.save(poll);
        subscriptionRepository.findByMessAndStatus(saved.getMess(), SubscriptionStatus.ACTIVE)
                .forEach(subscription -> notify(subscription.getStudent(), "Sunday special published", saved.getMess().getName() + " winner: " + saved.getWinningDish()));
        return saved;
    }

    public Map<String, Object> dashboard(User provider, Long messId) {
        Mess mess = ownedMess(provider, messId);
        List<Subscription> subscriptions = subscriptionRepository.findByMess(mess);
        long activeSubscriptions = subscriptions.stream()
                .filter(subscription -> subscription.getStatus() == SubscriptionStatus.ACTIVE)
                .count();
        long pendingPayments = subscriptions.stream()
                .filter(subscription -> subscription.getPaymentStatus() != PaymentStatus.PAID)
                .count();
        double earnings = subscriptions.stream()
                .filter(subscription -> subscription.getPaymentStatus() == PaymentStatus.PAID)
                .mapToDouble(subscription -> subscription.getAmountCharged().doubleValue())
                .sum();

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("messId", mess.getId());
        result.put("monthlyEarnings", earnings);
        result.put("activeSubscriptions", activeSubscriptions);
        result.put("paymentPendingCount", pendingPayments);
        result.put("todayAttendance", attendanceRepository.findByMessAndMealDate(mess, LocalDate.now()).size());
        result.put("todayBookings", mealBookingRepository.findByMessAndMealDate(mess, LocalDate.now()).size());
        result.put("openComplaints", complaintRepository.findByMess(mess).stream()
                .filter(complaint -> complaint.getStatus() != ComplaintStatus.RESOLVED)
                .count());
        return result;
    }

    private Mess ownedMess(User provider, Long messId) {
        Mess mess = messRepository.findById(messId).orElseThrow(() -> new IllegalArgumentException("Mess not found"));
        if (!mess.getProvider().getId().equals(provider.getId())) {
            throw new IllegalArgumentException("You do not own this mess");
        }
        return mess;
    }

    private void notify(User user, String title, String message) {
        notificationRepository.save(Notification.builder()
                .user(user)
                .title(title)
                .message(message)
                .readFlag(false)
                .build());
    }
}
