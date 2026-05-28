package com.eomcs.cleancode.ch11.exam05;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

// 네이티브 AspectJ 문법(.aj 파일)과 동일한 역할을 @Aspect 어노테이션 스타일로 표현한다
//
// 네이티브 문법:
//   public aspect LoggingAspect {
//       pointcut bankMethods(): execution(* com.eomcs.cleancode.ch11.exam05.Bank+.*(..));
//       Object around(): bankMethods() { ... }
//   }
//
// Spring AOP와 달리 프록시를 생성하지 않는다.
// aspectjweaver 에이전트(-javaagent)가 BankImpl 클래스 로드 시점에 로깅 코드를 바이트코드에 직접 삽입한다.
@Aspect
public class LoggingAspect {

  @Pointcut("execution(* com.eomcs.cleancode.ch11.exam05.Bank+.*(..))")
  public void bankMethods() {}

  @Around("bankMethods()")
  public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
    String methodName = joinPoint.getSignature().getName();

    System.out.println("before: " + methodName);

    Object result = joinPoint.proceed();

    System.out.println("after: " + methodName);

    return result;
  }
}
