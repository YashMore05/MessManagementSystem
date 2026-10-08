package com.messmanagement.repository;

import com.messmanagement.entity.MessProviderProfile;
import com.messmanagement.entity.User;
import com.messmanagement.enums.ProviderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessProviderProfileRepository extends JpaRepository<MessProviderProfile, Long> {
    Optional<MessProviderProfile> findByUser(User user);
    List<MessProviderProfile> findByStatus(ProviderStatus status);
}
