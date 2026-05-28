package com.eomcs.cleancode.ch11.exam04;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

// 로깅 횡단 관심사를 담당한다 — BankImpl에는 로깅 코드가 없다
@Aspect
@Component
public class LoggingAspect {

  @Around("execution(* com.eomcs.cleancode.ch11.exam04.*.*(..))")
  public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
    String methodName = joinPoint.getSignature().getName();

    System.out.println("before: " + methodName);

    Object result = joinPoint.proceed();

    System.out.println("after: " + methodName);

    return result;
  }
}
