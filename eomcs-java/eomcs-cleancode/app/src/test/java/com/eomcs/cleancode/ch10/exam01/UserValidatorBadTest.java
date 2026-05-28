package com.eomcs.cleancode.ch10.exam01;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch10.exam01.BadAndGood3.BadUserValidator;
import com.eomcs.cleancode.ch10.exam01.BadAndGood3.User;
import org.junit.jupiter.api.Test;

// Bad 테스트 코드 - UserValidator
//
// 문제점:
// - protected/public 보조 메서드를 직접 호출한다 (구현 세부사항에 의존).
// - isBlockedDomain()이 테스트 때문에 protected로 열려 있어
//   외부 하위 클래스가 내부 판단 로직에 의존할 수 있다.
// - 내부 보조 메서드가 클래스의 확장 포인트처럼 보인다.
// - 리팩터링 시 private으로 변경하면 테스트가 깨진다.
class UserValidatorBadTest {

  @Test
  void 차단된_도메인인지_직접_검증한다() {
    BadUserValidator validator = new BadUserValidator();

    assertTrue(validator.isBlockedDomain("kim@spam.com"));   // protected 직접 호출
    assertFalse(validator.isBlockedDomain("kim@example.com"));
  }

  @Test
  void 유효한_이메일인지_직접_검증한다() {
    BadUserValidator validator = new BadUserValidator();

    assertTrue(validator.hasValidEmail(new User("kim@example.com")));  // public 보조 메서드 직접 호출
    assertFalse(validator.hasValidEmail(new User("kim.example.com")));
  }
}
