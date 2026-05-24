package com.eomcs.cleancode.ch03.exam08;

public class BadAndGood1 {

  static class User {
    private boolean active;
    User(boolean active) { this.active = active; }
    void setActive(boolean active) { this.active = active; }
    boolean isActive() { return active; }
  }

  // Bad
  // - setActive → 상태 변경(Command)과 값 반환(Query)을 동시에 수행한다.
  // - 호출부에서 반환값의 의미가 "상태 확인"인지 "설정 결과 확인"인지 알기 어렵다.
  static class BadUserService {
    boolean setActive(User user) {
      user.setActive(true); // 상태 변경 (Command)
      return user.isActive(); // 값 반환 (Query)
    }
  }

  // Good: Command와 Query를 분리한다.
  // - activateUser → 상태 변경만 담당한다. 반환값이 없으므로 Command임이 명확하다.
  // - isUserActive → 값만 반환한다. 상태 변경이 없으므로 Query임이 명확하다.
  // - 호출부에서 각 함수의 역할과 의도가 명확하게 드러난다.
  static class GoodUserService {
    void activateUser(User user) {
      user.setActive(true);
    }

    boolean isUserActive(User user) {
      return user.isActive();
    }
  }

  static class GoodUsage {
    void demo(GoodUserService service, User user) {
      service.activateUser(user); // 명확한 Command

      if (service.isUserActive(user)) { // 명확한 Query
        System.out.println("사용자가 활성 상태입니다.");
      }
    }
  }
}
