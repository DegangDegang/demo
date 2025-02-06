package com.example.demo.global.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import com.example.demo.domain.notification.domain.NotifyInfo;
import com.example.demo.domain.notification.service.NotificationService;

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
		notificationService.send(notifyProxy.getReceiver(), notifyProxy.getNotificationType(), notifyProxy.getContent(),
			notifyProxy.getGoUrl());
		log.info("result = {}", result);
	}
}
