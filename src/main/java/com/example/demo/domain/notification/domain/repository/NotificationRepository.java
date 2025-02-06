package com.example.demo.domain.notification.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.domain.notification.domain.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
