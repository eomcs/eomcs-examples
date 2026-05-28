package com.eomcs.cleancode.ch12.exam03;

import static org.junit.jupiter.api.Assertions.*;

import com.eomcs.cleancode.ch12.exam03.BadAndGood2.User;
import com.eomcs.cleancode.ch12.exam03.BadAndGood2.UserService;
import org.junit.jupiter.api.Test;

// 예제 2: 의도를 표현하라
//
// isLoginAllowed()는 로그인 가능 조건을 도메인 언어로 표현한다.
// UserService.canLogin()은 위임만 하므로 의도가 명확하다.
class BadAndGood2Test {

  @Test
  void 활성화되고_잠기지_않고_실패_횟수가_적으면_로그인_가능하다() {
    User user = new User(true, false, 0);
    UserService service = new UserService();

    assertTrue(service.canLogin(user));
  }

  @Test
  void 비활성화된_사용자는_로그인할_수_없다() {
    User user = new User(false, false, 0);
    UserService service = new UserService();

    assertFalse(service.canLogin(user));
  }

  @Test
  void 잠긴_사용자는_로그인할_수_없다() {
    User user = new User(true, true, 0);
    UserService service = new UserService();

    assertFalse(service.canLogin(user));
  }

  @Test
  void 로그인_실패_횟수가_5회_이상이면_로그인할_수_없다() {
    User user = new User(true, false, 5);
    UserService service = new UserService();

    assertFalse(service.canLogin(user));
  }
}
