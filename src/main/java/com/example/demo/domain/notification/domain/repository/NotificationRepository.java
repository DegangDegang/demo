package com.example.demo.domain.notification.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.domain.notification.domain.Notification;
import com.example.demo.domain.notification.domain.TargetType;
import com.example.demo.domain.user.domain.User;

import feign.Param;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

	long countByReceiverAndIsRaed(User receiver, Boolean isRaed);

	Slice<Notification> findByReceiver(User receiver, Pageable pageable);

	@Query("SELECT n FROM Notification n WHERE n.sender.id = :senderId " +
		"AND n.receiver.id = :receiverId " +
		"AND n.targetType = :targetType " +
		"AND n.targetId = :targetId")
	Optional<Notification> findNotification(
		@Param("senderId") Long senderId,
		@Param("receiverId") Long receiverId,
		@Param("targetType") TargetType targetType,
		@Param("targetId") Long targetId);

}
