package com.messmanagement.repository;

import com.messmanagement.entity.LeaveRequest;
import com.messmanagement.entity.Mess;
import com.messmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByStudent(User student);
    List<LeaveRequest> findByMess(Mess mess);
}
