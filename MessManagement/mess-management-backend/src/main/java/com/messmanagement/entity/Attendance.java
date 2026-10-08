package com.messmanagement.entity;

import com.messmanagement.enums.MealType;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "mess_id", "meal_date", "meal_type"}))
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    private User student;
    @ManyToOne(optional = false)
    private Mess mess;
    @Column(name = "meal_date")
    private LocalDate mealDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "meal_type")
    private MealType mealType;
    private LocalDateTime scannedAt;
    public static class AttendanceBuilder {
        private Long id;
        private User student;
        private Mess mess;
        private LocalDate mealDate;
        private MealType mealType;
        private LocalDateTime scannedAt;
        AttendanceBuilder() {
        }

        
        public Attendance.AttendanceBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        
        public Attendance.AttendanceBuilder student(final User student) {
            this.student = student;
            return this;
        }

        
        public Attendance.AttendanceBuilder mess(final Mess mess) {
            this.mess = mess;
            return this;
        }

        
        public Attendance.AttendanceBuilder mealDate(final LocalDate mealDate) {
            this.mealDate = mealDate;
            return this;
        }

        
        public Attendance.AttendanceBuilder mealType(final MealType mealType) {
            this.mealType = mealType;
            return this;
        }

        
        public Attendance.AttendanceBuilder scannedAt(final LocalDateTime scannedAt) {
            this.scannedAt = scannedAt;
            return this;
        }
        public Attendance build() {
            return new Attendance(this.id, this.student, this.mess, this.mealDate, this.mealType, this.scannedAt);
        }

        @java.lang.Override
        public java.lang.String toString() {
            return "Attendance.AttendanceBuilder(id=" + this.id + ", student=" + this.student + ", mess=" + this.mess + ", mealDate=" + this.mealDate + ", mealType=" + this.mealType + ", scannedAt=" + this.scannedAt + ")";
        }
    }
    public static Attendance.AttendanceBuilder builder() {
        return new Attendance.AttendanceBuilder();
    }
    public Long getId() {
        return this.id;
    }
    public User getStudent() {
        return this.student;
    }
    public Mess getMess() {
        return this.mess;
    }
    public LocalDate getMealDate() {
        return this.mealDate;
    }
    public MealType getMealType() {
        return this.mealType;
    }
    public LocalDateTime getScannedAt() {
        return this.scannedAt;
    }
    public void setId(final Long id) {
        this.id = id;
    }
    public void setStudent(final User student) {
        this.student = student;
    }
    public void setMess(final Mess mess) {
        this.mess = mess;
    }
    public void setMealDate(final LocalDate mealDate) {
        this.mealDate = mealDate;
    }
    public void setMealType(final MealType mealType) {
        this.mealType = mealType;
    }
    public void setScannedAt(final LocalDateTime scannedAt) {
        this.scannedAt = scannedAt;
    }
    public Attendance() {
    }
    public Attendance(final Long id, final User student, final Mess mess, final LocalDate mealDate, final MealType mealType, final LocalDateTime scannedAt) {
        this.id = id;
        this.student = student;
        this.mess = mess;
        this.mealDate = mealDate;
        this.mealType = mealType;
        this.scannedAt = scannedAt;
    }
}
