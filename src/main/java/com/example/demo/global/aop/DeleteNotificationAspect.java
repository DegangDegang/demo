package com.example.demo.global.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import com.example.demo.domain.notification.domain.DeleteNotificationInfo;
import com.example.demo.domain.notification.domain.NotificationType;
import com.example.demo.domain.notification.domain.NotifyInfo;
import com.example.demo.domain.notification.service.NotificationService;
import com.example.demo.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Aspect
@Slf4j
@Component
@EnableAsync
@RequiredArgsConstructor
public class DeleteNotificationAspect {

	private final NotificationService notificationService;

	@Pointcut("@annotation(com.example.demo.global.aop.DeleteNotification)")
	public void annotationPointcut() {
	}

	@Async
	@AfterReturning(pointcut = "annotationPointcut()", returning = "result")
	public void checkValue(final JoinPoint joinPoint, final Object result) {
		DeleteNotificationInfo notifyProxy = (DeleteNotificationInfo)result;

		notificationService.deleteNotification(notifyProxy.getSender().getUserId(), notifyProxy.getReceiver().getUserId(),
			notifyProxy.getTargetType(), notifyProxy.getTargetId());

		log.info("sender: {}, receiver: {}", notifyProxy.getSender().getUserId(), notifyProxy.getReceiver().getUserId());

	}
}
