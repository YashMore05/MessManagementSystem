package com.messmanagement.service;

import com.messmanagement.dto.*;
import com.messmanagement.entity.*;
import com.messmanagement.enums.*;
import com.messmanagement.repository.*;
import com.messmanagement.util.DistanceUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;

@Service
public class StudentService {
    private final MessRepository messRepository;
    private final MenuItemRepository menuItemRepository;
    private final MonthlyPlanRepository monthlyPlanRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final MealBookingRepository mealBookingRepository;
    private final ReviewRepository reviewRepository;
    private final ComplaintRepository complaintRepository;
    private final PollRepository pollRepository;
    private final PollOptionRepository pollOptionRepository;
    private final PollVoteRepository pollVoteRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final NotificationRepository notificationRepository;
    private final NoticeRepository noticeRepository;
    private final NoticeReadRepository noticeReadRepository;
    private final QrTokenService qrTokenService;
    private final MessProviderProfileRepository providerProfileRepository;

    public StudentService(MessRepository messRepository, MenuItemRepository menuItemRepository,
                          MonthlyPlanRepository monthlyPlanRepository, SubscriptionRepository subscriptionRepository,
                          MealBookingRepository mealBookingRepository, ReviewRepository reviewRepository, ComplaintRepository complaintRepository,
                          PollRepository pollRepository, PollOptionRepository pollOptionRepository,
                          PollVoteRepository pollVoteRepository, LeaveRequestRepository leaveRequestRepository,
                          NotificationRepository notificationRepository, NoticeRepository noticeRepository,
                          NoticeReadRepository noticeReadRepository, QrTokenService qrTokenService,
                          MessProviderProfileRepository providerProfileRepository) {
        this.messRepository = messRepository;
        this.menuItemRepository = menuItemRepository;
        this.monthlyPlanRepository = monthlyPlanRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.mealBookingRepository = mealBookingRepository;
        this.reviewRepository = reviewRepository;
        this.complaintRepository = complaintRepository;
        this.pollRepository = pollRepository;
        this.pollOptionRepository = pollOptionRepository;
        this.pollVoteRepository = pollVoteRepository;
        this.leaveRequestRepository = leaveRequestRepository;
        this.notificationRepository = notificationRepository;
        this.noticeRepository = noticeRepository;
        this.noticeReadRepository = noticeReadRepository;
        this.qrTokenService = qrTokenService;
        this.providerProfileRepository = providerProfileRepository;
    }

    public List<Mess> searchMess(String area, MessType messType, Double lat, Double lng, Double radiusKm) {
        List<Mess> messes = area == null || area.isBlank()
                ? messRepository.findByActiveTrue()
                : messRepository.findByAreaContainingIgnoreCaseAndActiveTrue(area);

        List<Mess> filtered = messes.stream()
                .filter(mess -> providerProfileRepository.findByUser(mess.getProvider())
                        .map(profile -> profile.getStatus() == ProviderStatus.ACTIVE)
                        .orElse(false))
                .filter(mess -> messType == null || mess.getMessType() == messType || mess.getMessType() == MessType.BOTH)
                .filter(mess -> lat == null || lng == null || radiusKm == null
                        || (mess.getLatitude() != null && mess.getLongitude() != null
                        && DistanceUtil.haversineKm(lat, lng, mess.getLatitude(), mess.getLongitude()) <= radiusKm))
                .sorted(Comparator.comparing(Mess::getName))
                .toList();

        for (Mess m : filtered) {
            List<MonthlyPlan> plansList = monthlyPlanRepository.findByMessAndActiveTrue(m);
            if (!plansList.isEmpty()) {
                BigDecimal minPrice = plansList.stream()
                        .map(MonthlyPlan::getPrice)
                        .min(BigDecimal::compareTo)
                        .orElse(BigDecimal.ZERO);
                m.setStartingPrice(minPrice);
            } else {
                m.setStartingPrice(BigDecimal.ZERO);
            }
        }

        return filtered;
    }

    public List<MenuItem> todayMenu(Long messId) {
        return menuItemRepository.findByMessAndMenuDate(getMess(messId), LocalDate.now());
    }

    public MealBooking bookMeal(User student, Long menuItemId) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new IllegalArgumentException("Menu item not found"));
        if (!LocalDate.now().equals(menuItem.getMenuDate())) {
            throw new IllegalArgumentException("Only today's meals can be booked");
        }
        boolean alreadyBooked = mealBookingRepository.findByStudentOrderByBookedAtDesc(student).stream()
                .anyMatch(booking -> booking.getMealDate().equals(menuItem.getMenuDate())
                        && booking.getMealType() == menuItem.getMealType()
                        && booking.getStatus() != BookingStatus.CANCELLED);
        if (alreadyBooked) {
            throw new IllegalArgumentException("You already booked this meal today");
        }
        enforceBookingCutoff(menuItem.getMealType());
        MealBooking booking = new MealBooking();
        booking.setStudent(student);
        booking.setMess(menuItem.getMess());
        booking.setMenuItem(menuItem);
        booking.setMealType(menuItem.getMealType());
        booking.setMealDate(menuItem.getMenuDate());
        booking.setAmount(menuItem.getPrice() != null ? menuItem.getPrice() : defaultMealPrice(menuItem.getMealType()));
        booking.setStatus(BookingStatus.BOOKED);
        MealBooking saved = mealBookingRepository.save(booking);
        saved.setQrToken(qrTokenService.createBookingToken(saved.getId(), student.getId(), saved.getMess().getId(), saved.getMealType(), saved.getMealDate()));
        MealBooking finalBooking = mealBookingRepository.save(saved);
        notify(student, "Meal booked", finalBooking.getMealType() + " booked at " + finalBooking.getMess().getName() + ". Pay to activate QR.");
        return finalBooking;
    }

    public MealBooking payMealBooking(User student, Long bookingId) {
        MealBooking booking = mealBookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Meal booking not found"));
        if (!booking.getStudent().getId().equals(student.getId())) {
            throw new IllegalArgumentException("Meal booking does not belong to you");
        }
        if (booking.getStatus() == BookingStatus.CONSUMED) {
            throw new IllegalArgumentException("Meal already consumed");
        }
        booking.setStatus(BookingStatus.PAID);
        booking.setPaidAt(java.time.LocalDateTime.now());
        MealBooking saved = mealBookingRepository.save(booking);
        notify(student, "Payment successful", saved.getMealType() + " is paid. QR is ready for scanning.");
        return saved;
    }

    public List<MealBooking> mealBookings(User student) {
        return mealBookingRepository.findByStudentOrderByBookedAtDesc(student);
    }

    public List<MonthlyPlan> plans(Long messId) {
        return monthlyPlanRepository.findByMessAndActiveTrue(getMess(messId));
    }

    public Subscription subscribe(User student, Long planId) {
        MonthlyPlan plan = monthlyPlanRepository.findById(planId)
                .orElseThrow(() -> new IllegalArgumentException("Plan not found"));
        LocalDate today = LocalDate.now();
        LocalDate end = today.withDayOfMonth(today.lengthOfMonth());

        return subscriptionRepository.save(Subscription.builder()
                .student(student)
                .plan(plan)
                .mess(plan.getMess())
                .startDate(today)
                .endDate(end)
                .amountCharged(plan.getPrice())
                .status(SubscriptionStatus.ACTIVE)
                .paymentStatus(PaymentStatus.CREATED)
                .build());
    }

    public List<Subscription> subscriptions(User student) {
        return subscriptionRepository.findByStudent(student);
    }

    public Subscription markSubscriptionPaid(User student, Long subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found"));
        if (!subscription.getStudent().getId().equals(student.getId())) {
            throw new IllegalArgumentException("Subscription does not belong to you");
        }
        subscription.setPaymentStatus(PaymentStatus.PAID);
        return subscriptionRepository.save(subscription);
    }

    public Review review(User student, ReviewRequest request) {
        return reviewRepository.save(Review.builder()
                .student(student)
                .mess(getMess(request.getMessId()))
                .mealType(request.getMealType())
                .mealDate(request.getMealDate())
                .rating(request.getRating())
                .comment(request.getComment())
                .build());
    }

    public Complaint complaint(User student, ComplaintRequest request) {
        Mess mess = getMess(request.getMessId());
        return complaintRepository.save(Complaint.builder()
                .student(student)
                .mess(mess)
                .assignedProvider(mess.getProvider())
                .category(request.getCategory())
                .title(request.getTitle())
                .description(request.getDescription())
                .status(ComplaintStatus.OPEN)
                .build());
    }

    public List<Complaint> complaints(User student) {
        return complaintRepository.findByStudent(student);
    }

    public List<Notice> notices(Long messId) {
        return noticeRepository.findByMessAndExpiryDateGreaterThanEqual(getMess(messId), LocalDate.now());
    }

    public NoticeRead markNoticeRead(User student, Long noticeId) {
        Notice notice = noticeRepository.findById(noticeId)
                .orElseThrow(() -> new IllegalArgumentException("Notice not found"));
        if (noticeReadRepository.existsByNoticeAndStudent(notice, student)) {
            return null;
        }
        NoticeRead read = new NoticeRead();
        read.setNotice(notice);
        read.setStudent(student);
        notice.setReadCount((notice.getReadCount() == null ? 0 : notice.getReadCount()) + 1);
        noticeRepository.save(notice);
        return noticeReadRepository.save(read);
    }

    public List<Poll> polls(Long messId) {
        return pollRepository.findByMessAndPollDate(getMess(messId), LocalDate.now());
    }

    public String qr(User student, Long messId, MealType mealType) {
        return qrTokenService.createToken(student.getId(), messId, mealType, LocalDate.now());
    }

    public PollVote vote(User student, Long pollId, Long optionId) {
        Poll poll = pollRepository.findById(pollId).orElseThrow(() -> new IllegalArgumentException("Poll not found"));
        if (pollVoteRepository.existsByStudentAndPoll(student, poll)) {
            throw new IllegalArgumentException("You already voted in this poll");
        }
        PollOption option = pollOptionRepository.findById(optionId)
                .orElseThrow(() -> new IllegalArgumentException("Poll option not found"));
        if (!option.getPoll().getId().equals(poll.getId())) {
            throw new IllegalArgumentException("Option does not belong to this poll");
        }
        return pollVoteRepository.save(PollVote.builder().student(student).poll(poll).option(option).build());
    }

    public LeaveRequest markLeave(User student, LeaveRequestDto request) {
        return leaveRequestRepository.save(LeaveRequest.builder()
                .student(student)
                .mess(getMess(request.getMessId()))
                .fromDate(request.getFromDate())
                .toDate(request.getToDate())
                .reason(request.getReason())
                .status(LeaveStatus.MARKED)
                .build());
    }

    public List<Notification> notifications(User student) {
        return notificationRepository.findByUserOrderByCreatedAtDesc(student);
    }

    private Mess getMess(Long messId) {
        return messRepository.findById(messId).orElseThrow(() -> new IllegalArgumentException("Mess not found"));
    }

    private BigDecimal defaultMealPrice(MealType mealType) {
        if (mealType == MealType.BREAKFAST) {
            return BigDecimal.valueOf(40);
        }
        if (mealType == MealType.LUNCH) {
            return BigDecimal.valueOf(80);
        }
        return BigDecimal.valueOf(70);
    }

    private void enforceBookingCutoff(MealType mealType) {
        java.time.LocalTime now = java.time.LocalTime.now();
        if (mealType == MealType.BREAKFAST && now.isAfter(java.time.LocalTime.of(11, 0))) {
            throw new IllegalArgumentException("Breakfast booking is closed after 11:00 AM");
        }
        if (mealType == MealType.LUNCH && now.isAfter(java.time.LocalTime.of(14, 0))) {
            throw new IllegalArgumentException("Lunch booking is closed after 2:00 PM");
        }
        if (mealType == MealType.DINNER && now.isAfter(java.time.LocalTime.of(22, 0))) {
            throw new IllegalArgumentException("Dinner booking is closed after 10:00 PM");
        }
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
