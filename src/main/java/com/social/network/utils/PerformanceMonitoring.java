package com.social.network.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class PerformanceMonitoring
{

	@Around("@annotation(SocialMethodVisit)")
	public Object monitorPerformance(ProceedingJoinPoint joinPoint) throws Throwable
	{
		long startTime = System.currentTimeMillis();
		Object result = joinPoint.proceed();
		long endTime = System.currentTimeMillis();
		log.info("Execution time of class : {} , method : {} and time taken is : {} ",
			joinPoint.getSignature().getDeclaringType().getClass().getName(), 
			joinPoint.getSignature().getName(), (endTime - startTime) + " ms");
		return result;
	}
}
