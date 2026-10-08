package com.messmanagement.repository;

import com.messmanagement.entity.Attendance;
import com.messmanagement.entity.Mess;
import com.messmanagement.entity.User;
import com.messmanagement.enums.MealType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Optional<Attendance> findByStudentAndMessAndMealDateAndMealType(User student, Mess mess, LocalDate mealDate, MealType mealType);
    List<Attendance> findByMessAndMealDate(Mess mess, LocalDate mealDate);
    long countByMessAndMealDateAndMealType(Mess mess, LocalDate mealDate, MealType mealType);
}
