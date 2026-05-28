package com.eomcs.cleancode.ch09.exam04;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch09.exam04.BadAndGood1.User;
import org.junit.jupiter.api.Test;

// 예제 1: 테스트당 하나의 개념 - User
//
// Bad: 사용자_정보를_검증한다() - 이름/이메일/나이를 한 테스트에서 모두 검증한다.
// Good: 각 속성을 별도 테스트로 분리해 테스트 이름이 하나의 기대를 명확히 말한다.
//       실패 원인이 즉시 드러나고, 테스트가 문서처럼 읽힌다.
class UserGoodTest {

  @Test
  void 사용자는_이름을_가진다() {
    User user = new User("kim", "kim@example.com", 20);

    assertEquals("kim", user.getName());
  }

  @Test
  void 사용자는_이메일을_가진다() {
    User user = new User("kim", "kim@example.com", 20);

    assertEquals("kim@example.com", user.getEmail());
  }

  @Test
  void 사용자는_나이를_가진다() {
    User user = new User("kim", "kim@example.com", 20);

    assertEquals(20, user.getAge());
  }
}
