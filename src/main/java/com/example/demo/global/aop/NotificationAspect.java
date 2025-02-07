package com.example.demo.global.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import com.example.demo.domain.notification.domain.NotificationType;
import com.example.demo.domain.notification.domain.NotifyInfo;
import com.example.demo.domain.notification.domain.TargetType;
import com.example.demo.domain.notification.service.NotificationService;
import com.example.demo.domain.shortessay.presentation.response.HostInfo;
import com.example.demo.domain.user.domain.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
@Component
@EnableAsync
@RequiredArgsConstructor
public class NotificationAspect {

	private final NotificationService notificationService;

	@Pointcut("@annotation(com.example.demo.global.aop.Notify)")
	public void annotationPointcut() {
	}

	@Async
	@AfterReturning(pointcut = "annotationPointcut()", returning = "result")
	public void checkValue(final JoinPoint joinPoint, final Object result) {
		NotifyInfo notifyProxy = (NotifyInfo)result;

		HostInfo sender = notifyProxy.getSender();

		if (notifyProxy.getNotificationType().equals(NotificationType.FOLLOW)) {
			notifyProxy.writeContent(String.format("%s님이 당신을 팔로우하였습니다.", sender.getNickname()));
			notifyProxy.writeGoUrl("api/v1/users/" + sender.getUserId());

		} else if (notifyProxy.getNotificationType().equals(NotificationType.LIKE)) {
			notifyProxy.writeContent(
				String.format("%s님이 당신의 %s을 좋아합니다.", sender.getNickname(), notifyProxy.getTargetType().getValue()));
			notifyProxy.writeGoUrl("api/v1/" + notifyProxy.getTargetType().getUrl() + "/" + notifyProxy.getTargetId());
		}

		notificationService.send(notifyProxy.getReceiver(), notifyProxy.getSender(), notifyProxy.getNotificationType(),
			notifyProxy.getTargetType(), notifyProxy.getTargetId(), notifyProxy.getContent(), notifyProxy.getGoUrl());
		log.info("result = {}", result);
	}
}
