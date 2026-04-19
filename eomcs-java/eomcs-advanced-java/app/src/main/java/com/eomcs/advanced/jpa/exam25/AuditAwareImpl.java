package com.eomcs.advanced.jpa.exam25;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;

// exam25 - AuditorAware 구현체
//
// @CreatedBy / @LastModifiedBy 필드에 채울 "현재 사용자"를 반환한다.
//
// 실제 웹 애플리케이션에서는 Spring Security의 SecurityContextHolder에서
// 인증된 사용자 이름을 꺼내 반환한다:
//
//   Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//   return Optional.ofNullable(auth).map(Authentication::getName);
//
// 이 예제에서는 단순화를 위해 고정 문자열을 반환한다.
//
public class AuditAwareImpl implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    // 실제 프로젝트: SecurityContextHolder.getContext().getAuthentication().getName()
    return Optional.of("system-user");
  }
}
