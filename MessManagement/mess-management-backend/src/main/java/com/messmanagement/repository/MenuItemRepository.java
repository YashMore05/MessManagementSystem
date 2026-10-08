package com.messmanagement.repository;

import com.messmanagement.entity.MenuItem;
import com.messmanagement.entity.Mess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    List<MenuItem> findByMessAndMenuDate(Mess mess, LocalDate menuDate);
    List<MenuItem> findByMenuDate(LocalDate menuDate);
}
