package com.eomcs.cleancode.ch09.exam05;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch09.exam05.BadAndGood4.User;
import org.junit.jupiter.api.Test;

// 예제 4: S — Self-Validating (스스로 검증)
//
// Bad: System.out.println()으로 사람이 결과를 확인한다.
// Good: assertEquals()로 테스트가 스스로 성공/실패를 판단한다.
//       자동화된 빌드에서 버그를 자동으로 감지할 수 있다.
class SelfValidatingGoodTest {

  @Test
  void 사용자_이름을_검증한다() {
    User user = new User("kim");

    assertEquals("kim", user.getName());
  }
}
