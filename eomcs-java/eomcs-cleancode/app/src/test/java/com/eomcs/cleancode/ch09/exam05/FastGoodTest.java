package com.eomcs.cleancode.ch09.exam05;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch09.exam05.BadAndGood1.FakeUserRepository;
import com.eomcs.cleancode.ch09.exam05.BadAndGood1.User;
import com.eomcs.cleancode.ch09.exam05.BadAndGood1.UserRepository;
import com.eomcs.cleancode.ch09.exam05.BadAndGood1.UserService;
import org.junit.jupiter.api.Test;

// 예제 1: F — Fast (빠르게 실행)
//
// Bad: Thread.sleep()으로 테스트가 느려진다.
// Good: 외부 시스템(DB, 네트워크) 대신 FakeRepository를 주입해
//       테스트가 빠르게 실행되고 자주 실행할 수 있다.
class FastGoodTest {

  @Test
  void 사용자_조회() {
    UserRepository fakeRepository = new FakeUserRepository();
    UserService service = new UserService(fakeRepository);

    User user = service.findUser(1L);

    assertEquals("kim", user.getName());
  }
}
