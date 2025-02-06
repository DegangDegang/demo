package com.example.demo.domain.notification.domain;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.demo.domain.notification.domain.vo.NotificationInfoVo;
import com.example.demo.domain.user.domain.User;
import com.example.demo.global.database.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notifications")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "notification_id")
	private Long id;

	private String content;

	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User receiver;

	private String url; // 관련된 url

	@Enumerated(EnumType.STRING)
	private NotificationType notificationType;

	private Boolean isRaed;

	@Builder
	public Notification(String content, User receiver, String url, NotificationType notificationType) {
		this.content = content;
		this.receiver = receiver;
		this.url = url;
		this.notificationType = notificationType;
		this.isRaed = false;
	}

	public NotificationInfoVo getNotificationInfo() {
		return NotificationInfoVo.builder()
			.notificationId(id)
			.content(content)
			.url(url)
			.isRead(isRaed)
			.userId(receiver.getId())
			.userNickname(receiver.getNickname())
			.userProfileImgUrl(receiver.getProfileImgUrl())
			.createdAt(getCreatedAt())
			.lastModifiedAt(getLastModifyAt())
			.build();

	}
}
