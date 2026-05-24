package com.eomcs.cleancode.ch03.exam11;

import java.util.List;

public class BadAndGood2 {

  static class User {
    private String name;
    private boolean active;
    User(String name, boolean active) { this.name = name; this.active = active; }
    String getName() { return name; }
    boolean isActive() { return active; }
  }

  // Bad: 단일 출구를 위해 불필요한 상태 변수를 도입한다.
  // - result, found 두 변수를 동시에 추적해야 한다.
  // - found가 true가 된 이후에도 루프가 계속 돌아 불필요한 순회가 발생한다.
  // - 코드의 의도(활성 사용자를 찾는다)가 흐려진다.
  static class BadUserFinder {
    User findActiveUser(List<User> users) {
      User result = null;
      boolean found = false;

      for (User user : users) {
        if (!found && user.isActive()) {
          result = user;
          found = true;
        }
      }

      return result;
    }
  }

  // Good: 작은 함수에서는 return을 허용한다.
  // - 활성 사용자를 찾으면 즉시 반환하므로 불필요한 순회가 없다.
  // - 불필요한 상태 변수(result, found)가 없다.
  // - 함수의 의도가 명확하게 드러난다.
  static class GoodUserFinder {
    User findActiveUser(List<User> users) {
      for (User user : users) {
        if (user.isActive()) {
          return user;
        }
      }

      return null;
    }
  }
}
