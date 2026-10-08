package com.messmanagement.repository;

import com.messmanagement.entity.Poll;
import com.messmanagement.entity.PollVote;
import com.messmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PollVoteRepository extends JpaRepository<PollVote, Long> {
    boolean existsByStudentAndPoll(User student, Poll poll);
    List<PollVote> findByPoll(Poll poll);

    @Query("select v.option.dishName, count(v) from PollVote v where v.poll = :poll group by v.option.dishName order by count(v) desc")
    List<Object[]> resultsForPoll(Poll poll);
}
