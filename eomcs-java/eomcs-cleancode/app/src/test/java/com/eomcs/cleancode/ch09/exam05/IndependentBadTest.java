package com.eomcs.cleancode.ch09.exam05;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch09.exam05.BadAndGood2.InMemoryUserRepository;
import com.eomcs.cleancode.ch09.exam05.BadAndGood2.User;
import com.eomcs.cleancode.ch09.exam05.BadAndGood2.UserService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

// Bad 테스트 코드 - I (Independent)
//
// 문제점:
// - 사용자_조회()는 사용자_생성()이 먼저 실행되어야만 통과한다.
// - 실행 순서에 의존하기 때문에 순서가 바뀌면 연쇄 실패가 발생한다.
// - 테스트 하나가 실패하면 다른 테스트도 함께 깨진다.
// - 디버깅이 어렵다.
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class IndependentBadTest {

  // 테스트 간 상태를 공유 - 의존성의 근본 원인
  static final UserService userService =
      new UserService(new InMemoryUserRepository());

  @Test
  @Order(1)
  void 사용자_생성() {
    userService.create("kim"); // 다음 테스트가 이 데이터에 의존한다
  }

  @Test
  @Order(2)
  void 사용자_조회() {
    User user = userService.find("kim"); // 이전 테스트(사용자_생성)에 의존

    assertEquals("kim", user.getName());
  }
}
