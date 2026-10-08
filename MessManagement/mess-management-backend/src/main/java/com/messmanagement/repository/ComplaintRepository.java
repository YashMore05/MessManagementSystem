package com.messmanagement.repository;

import com.messmanagement.entity.Complaint;
import com.messmanagement.entity.Mess;
import com.messmanagement.entity.User;
import com.messmanagement.enums.ComplaintStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
    List<Complaint> findByStudent(User student);
    List<Complaint> findByMess(Mess mess);
    List<Complaint> findByStatus(ComplaintStatus status);
}
