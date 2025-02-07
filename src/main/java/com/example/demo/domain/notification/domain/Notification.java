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
	@JoinColumn(name = "receiver_id")
	private User receiver;

	@OnDelete(action = OnDeleteAction.CASCADE)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sender_id")
	private User sender;

	private String url; // 관련된 url

	@Enumerated(EnumType.STRING)
	private NotificationType notificationType;

	@Enumerated(EnumType.STRING)
	private TargetType targetType;

	private Long targetId;

	private Boolean isRaed;

	@Builder
	public Notification(String content, User receiver, User sender, String url, NotificationType notificationType,
		TargetType targetType, Long targetId) {
		this.content = content;
		this.receiver = receiver;
		this.sender = sender;
		this.url = url;
		this.notificationType = notificationType;
		this.targetType = targetType;
		this.targetId = targetId;
		this.isRaed = false;
	}

	public NotificationInfoVo getNotificationInfo() {
		return NotificationInfoVo.builder()
			.notificationId(id)
			.content(content)
			.url(url)
			.isRead(isRaed)
			.type(notificationType.getValue())
			.receiverId(receiver.getId())
			.receiverNickname(receiver.getNickname())
			.receiverProfileImgUrl(receiver.getProfileImgUrl())
			.senderId(sender.getId())
			.senderNickname(sender.getNickname())
			.senderProfileImgUrl(sender.getProfileImgUrl())
			.createdAt(getCreatedAt())
			.lastModifiedAt(getLastModifyAt())
			.build();

	}

	public void read() {
		this.isRaed = true;
	}
}
