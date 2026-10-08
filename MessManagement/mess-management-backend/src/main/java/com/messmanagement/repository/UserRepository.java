package com.messmanagement.repository;

import com.messmanagement.entity.User;
import com.messmanagement.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByCollegeId(String collegeId);
    List<User> findByRole(Role role);
    long countByRole(Role role);
}
