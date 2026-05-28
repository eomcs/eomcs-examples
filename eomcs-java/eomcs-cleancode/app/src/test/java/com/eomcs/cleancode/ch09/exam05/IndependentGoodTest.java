package com.eomcs.cleancode.ch09.exam05;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch09.exam05.BadAndGood2.InMemoryUserRepository;
import com.eomcs.cleancode.ch09.exam05.BadAndGood2.User;
import com.eomcs.cleancode.ch09.exam05.BadAndGood2.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

// 예제 2: I — Independent (독립적)
//
// Bad: 테스트가 실행 순서에 의존한다.
// Good: @BeforeEach로 매 테스트마다 새 상태를 만들고,
//       각 테스트가 필요한 데이터를 스스로 준비해 독립적으로 실행된다.
class IndependentGoodTest {

  UserService userService;

  @BeforeEach
  void setUp() {
    userService = new UserService(new InMemoryUserRepository());
  }

  @Test
  void 사용자_조회() {
    userService.create("kim"); // 이 테스트가 직접 준비한다

    User user = userService.find("kim");

    assertEquals("kim", user.getName());
  }
}
