package com.messmanagement.repository;

import com.messmanagement.entity.Mess;
import com.messmanagement.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findByMessAndExpiryDateGreaterThanEqual(Mess mess, LocalDate today);
}
