package com.MongMoong.MongBitProject.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 컨트롤러 패키지에 대한 Pointcut 정의
    @Pointcut("execution(* com.MongMoong.MongBitProject..*(..))")
    public void projectPackagePointcut() {}

    @Before("projectPackagePointcut()") // 메소드 실행 전
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Executing: " + joinPoint.getSignature().toShortString());
    }

    @AfterReturning(value = "projectPackagePointcut()", returning = "result") // 메소드 실행 후
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("Executed: " + joinPoint.getSignature().toShortString() + ", Return: " + result);
    }
}
