package com.messmanagement.repository;

import com.messmanagement.entity.MealBooking;
import com.messmanagement.entity.Mess;
import com.messmanagement.entity.User;
import com.messmanagement.enums.BookingStatus;
import com.messmanagement.enums.MealType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MealBookingRepository extends JpaRepository<MealBooking, Long> {
    List<MealBooking> findByStudentOrderByBookedAtDesc(User student);
    List<MealBooking> findByMessAndMealDate(Mess mess, LocalDate mealDate);
    Optional<MealBooking> findByQrToken(String qrToken);
    long countByMessAndMealDateAndMealType(Mess mess, LocalDate mealDate, MealType mealType);
    long countByMessAndMealDateAndMealTypeAndStatus(Mess mess, LocalDate mealDate, MealType mealType, BookingStatus status);
}
