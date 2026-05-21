package com.eomcs.cleancode.ch02.exam09;

import java.time.LocalDateTime;

public class BadAndGood6 {

  static class User {
    String id;
    LocalDateTime lastLogin;
  }

  // Bad
  // - getUser() 라는 이름은 조회만 할 것 같지만, 실제로는 DB 업데이트도 수행한다.
  // - 이름이 부작용을 숨겨 예측 불가능한 코드가 된다.
  static class BadUserRepository {
    User getUser(String id) {
      User user = findInDatabase(id);
      updateLastAccess(user); // 부작용이 이름에 드러나지 않음
      return user;
    }
    User findInDatabase(String id) { return null; }
    void updateLastAccess(User user) {}
  }

  // Good
  // - findUser → 조회만 수행한다는 의도가 명확하다.
  // - updateUserLastLogin → 업데이트 행위를 별도 메서드로 분리해 부작용을 숨기지 않는다.
  static class GoodUserRepository {
    User findUser(String id) {
      return findInDatabase(id);
    }
    void updateUserLastLogin(User user) {
      user.lastLogin = LocalDateTime.now();
    }
    User findInDatabase(String id) { return null; }
  }
}
