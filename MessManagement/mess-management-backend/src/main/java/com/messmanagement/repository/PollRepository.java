package com.messmanagement.repository;

import com.messmanagement.entity.Mess;
import com.messmanagement.entity.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PollRepository extends JpaRepository<Poll, Long> {
    List<Poll> findByMessAndPollDate(Mess mess, LocalDate pollDate);
    List<Poll> findByPollDate(LocalDate pollDate);
}
