package com.eomcs.cleancode.ch09.exam04;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch09.exam04.BadAndGood4.InvalidEmailException;
import com.eomcs.cleancode.ch09.exam04.BadAndGood4.User;
import com.eomcs.cleancode.ch09.exam04.BadAndGood4.UserService;
import com.eomcs.cleancode.ch09.exam04.BadAndGood4.WeakPasswordException;
import org.junit.jupiter.api.Test;

// Bad 테스트 코드 - UserService
//
// 문제점:
// - 이메일 검증, 비밀번호 검증, 정상 가입을 한 테스트에서 모두 다룬다.
// - 테스트 이름이 너무 넓다.
// - 테스트가 여러 이유로 실패할 수 있다.
// - 하나의 요구사항 문서로 읽히지 않는다.
class UserServiceBadTest {

  @Test
  void 회원가입_검증() {
    UserService service = new UserService();

    assertThrows(InvalidEmailException.class, () -> {
      service.register("bad-email", "password123");
    });

    assertThrows(WeakPasswordException.class, () -> {
      service.register("kim@example.com", "123");
    });

    User user = service.register("kim@example.com", "password123");

    assertEquals("kim@example.com", user.getEmail());
  }
}
