package com.messmanagement.repository;

import com.messmanagement.entity.Mess;
import com.messmanagement.entity.MonthlyPlan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MonthlyPlanRepository extends JpaRepository<MonthlyPlan, Long> {
    List<MonthlyPlan> findByMessAndActiveTrue(Mess mess);
}
