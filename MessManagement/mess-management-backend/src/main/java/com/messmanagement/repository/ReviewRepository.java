package com.messmanagement.repository;

import com.messmanagement.entity.Mess;
import com.messmanagement.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByMess(Mess mess);

    @Query("select coalesce(avg(r.rating), 0) from Review r where r.mess = :mess")
    Double averageRatingForMess(Mess mess);
}
