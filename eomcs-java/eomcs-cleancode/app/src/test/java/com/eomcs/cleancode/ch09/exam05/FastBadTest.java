package com.eomcs.cleancode.ch09.exam05;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch09.exam05.BadAndGood1.SlowUserRepository;
import com.eomcs.cleancode.ch09.exam05.BadAndGood1.User;
import com.eomcs.cleancode.ch09.exam05.BadAndGood1.UserService;
import org.junit.jupiter.api.Test;

// Bad 테스트 코드 - F (Fast)
//
// 문제점:
// - Thread.sleep()으로 테스트 실행이 느려진다.
// - 개발자가 테스트 실행을 꺼리게 된다.
// - 피드백 속도가 떨어진다.
// - DB, 네트워크 등 외부 시스템에 의존하면 같은 문제가 발생한다.
class FastBadTest {

  UserService userService = new UserService(new SlowUserRepository());

  @Test
  void 사용자_조회() throws InterruptedException {
    Thread.sleep(500); // 느린 테스트 (실제 DB 호출을 흉내 냄)

    User user = userService.findUser(1L);

    assertEquals("kim", user.getName());
  }
}
