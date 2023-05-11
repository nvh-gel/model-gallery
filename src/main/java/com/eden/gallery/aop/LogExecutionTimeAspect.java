package com.eden.gallery.aop;

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Aspect for logging method execution duration.
 */
@Aspect
@Component
@Log4j2
public class LogExecutionTimeAspect {

    /**
     * Log method execution call time and duration.
     *
     * @param joinPoint method join point
     * @return method call result
     * @throws Throwable if method call throw an exception
     */
    @Around("@annotation(LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        LocalDateTime start = LocalDateTime.now();
        log.info("Request to {} received at {} with params {}",
                joinPoint.getSignature(), LocalDateTime.now(), joinPoint.getArgs());
        Object proceed = joinPoint.proceed();
        LocalDateTime end = LocalDateTime.now();
        log.info("Request complete for {}, duration {}ms",
                joinPoint.getSignature(), Duration.between(start, end).toMillis());
        return proceed;
    }
}
