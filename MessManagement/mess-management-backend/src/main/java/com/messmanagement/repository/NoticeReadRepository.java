package com.messmanagement.repository;

import com.messmanagement.entity.Notice;
import com.messmanagement.entity.NoticeRead;
import com.messmanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeReadRepository extends JpaRepository<NoticeRead, Long> {
    boolean existsByNoticeAndStudent(Notice notice, User student);
}
