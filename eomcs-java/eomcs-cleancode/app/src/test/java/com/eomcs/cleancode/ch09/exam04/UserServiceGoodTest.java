package com.eomcs.cleancode.ch09.exam04;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch09.exam04.BadAndGood4.InvalidEmailException;
import com.eomcs.cleancode.ch09.exam04.BadAndGood4.User;
import com.eomcs.cleancode.ch09.exam04.BadAndGood4.UserService;
import com.eomcs.cleancode.ch09.exam04.BadAndGood4.WeakPasswordException;
import org.junit.jupiter.api.Test;

// 예제 4: 테스트당 하나의 개념 - UserService
//
// Bad: 회원가입_검증() - 이메일/비밀번호/정상 가입을 한 테스트에서 모두 다룬다.
// Good: 각 검증 조건을 별도 테스트로 분리해 테스트 이름이 요구사항 목록처럼 읽힌다.
//       실패 원인이 명확하고, 테스트 유지보수가 쉬워진다.
class UserServiceGoodTest {

  @Test
  void 이메일_형식이_잘못되면_예외를_던진다() {
    UserService service = new UserService();

    assertThrows(InvalidEmailException.class, () -> {
      service.register("bad-email", "password123");
    });
  }

  @Test
  void 비밀번호가_약하면_예외를_던진다() {
    UserService service = new UserService();

    assertThrows(WeakPasswordException.class, () -> {
      service.register("kim@example.com", "123");
    });
  }

  @Test
  void 올바른_정보로_회원가입하면_사용자를_생성한다() {
    UserService service = new UserService();

    User user = service.register("kim@example.com", "password123");

    assertEquals("kim@example.com", user.getEmail());
  }
}
