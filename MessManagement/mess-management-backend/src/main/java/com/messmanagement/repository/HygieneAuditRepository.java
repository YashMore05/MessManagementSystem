package com.messmanagement.repository;

import com.messmanagement.entity.HygieneAudit;
import com.messmanagement.entity.Mess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HygieneAuditRepository extends JpaRepository<HygieneAudit, Long> {
    List<HygieneAudit> findByMess(Mess mess);
}
