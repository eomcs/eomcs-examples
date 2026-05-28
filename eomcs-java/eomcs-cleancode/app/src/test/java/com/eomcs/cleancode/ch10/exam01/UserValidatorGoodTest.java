package com.eomcs.cleancode.ch10.exam01;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch10.exam01.BadAndGood3.User;
import com.eomcs.cleancode.ch10.exam01.BadAndGood3.UserValidator;
import org.junit.jupiter.api.Test;

// 예제 3: 클래스 체계 - UserValidator
//
// Bad: protected/public 보조 메서드를 직접 테스트해 구현 세부사항에 의존한다.
// Good: private 메서드는 직접 테스트하지 않는다.
//       public 메서드(validate)의 결과로 내부 동작을 검증한다.
//       캡슐화를 유지하고, 테스트가 클래스의 행위에 집중한다.
class UserValidatorGoodTest {

  @Test
  void 차단된_도메인이면_유효하지_않다() {
    UserValidator validator = new UserValidator();

    boolean valid = validator.validate(new User("kim@spam.com"));

    assertFalse(valid);
  }

  @Test
  void 이메일에_골뱅이가_없으면_유효하지_않다() {
    UserValidator validator = new UserValidator();

    boolean valid = validator.validate(new User("kim.example.com"));

    assertFalse(valid);
  }

  @Test
  void 올바른_이메일이면_유효하다() {
    UserValidator validator = new UserValidator();

    boolean valid = validator.validate(new User("kim@example.com"));

    assertTrue(valid);
  }
}
