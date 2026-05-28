package com.eomcs.cleancode.ch09.exam04;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch09.exam04.BadAndGood1.User;
import org.junit.jupiter.api.Test;

// Bad 테스트 코드 - User
//
// 문제점:
// - 이름, 이메일, 나이를 한 테스트에서 모두 검증한다.
// - 실패했을 때 어떤 개념이 깨졌는지 테스트 이름만으로 알기 어렵다.
// - 테스트 이름이 너무 포괄적이다.
// - 하나의 테스트가 여러 이유로 실패할 수 있다.
class UserBadTest {

  @Test
  void 사용자_정보를_검증한다() {
    User user = new User("kim", "kim@example.com", 20);

    assertEquals("kim", user.getName());
    assertEquals("kim@example.com", user.getEmail());
    assertEquals(20, user.getAge());
  }
}
