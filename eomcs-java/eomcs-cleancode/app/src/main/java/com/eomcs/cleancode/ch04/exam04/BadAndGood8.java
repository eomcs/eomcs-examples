package com.eomcs.cleancode.ch04.exam04;

import java.util.List;

public class BadAndGood8 {

  static class User {
    private boolean active;
    private int point;
    User(boolean active, int point) { this.active = active; this.point = point; }
    boolean isActive() { return active; }
    int getPoint() { return point; }
  }

  static class Module {
    private List<String> dependSubsystems;
    Module(List<String> deps) { this.dependSubsystems = deps; }
    List<String> getDependSubsystems() { return dependSubsystems; }
  }

  static class SubSystemModule {
    private String subSystem;
    SubSystemModule(String subSystem) { this.subSystem = subSystem; }
    String getSubSystem() { return subSystem; }
  }

  // Bad: 함수나 변수로 표현할 수 있는데 주석을 사용한다
  //      (Don't Use a Comment When You Can Use a Function or a Variable)
  // - 조건식의 의미를 주석으로 설명하는 대신 메서드나 변수로 표현해야 한다.
  // - 주석은 코드가 바뀌면 거짓말이 될 수 있지만, 메서드 이름은 항상 동기화된다.
  static class BadDiscountService {
    void applyDiscount(User user) { System.out.println("할인 적용"); }

    void processDiscount(User user) {
      // check if user can receive discount
      if (user.isActive() && user.getPoint() > 1000) {
        applyDiscount(user);
      }
    }

    void checkDependency(Module smodule, SubSystemModule subSysMod) {
      // does the module from the global list depend on the subsystem we are part of?
      if (smodule.getDependSubsystems().contains(subSysMod.getSubSystem())) {
        System.out.println("의존성 존재");
      }
    }
  }

  // Good: 조건은 메서드로, 중간값은 변수로 추출하여 주석을 제거한다.
  // - canReceiveDiscount()라는 이름이 조건의 의미를 정확히 전달한다.
  // - moduleDependees, ourSubSystem 변수가 각 값의 역할을 명확히 드러낸다.
  static class GoodDiscountService {
    void applyDiscount(User user) { System.out.println("할인 적용"); }

    void processDiscount(User user) {
      if (canReceiveDiscount(user)) {
        applyDiscount(user);
      }
    }

    private boolean canReceiveDiscount(User user) {
      return user.isActive() && user.getPoint() > 1000;
    }

    void checkDependency(Module smodule, SubSystemModule subSysMod) {
      List<String> moduleDependees = smodule.getDependSubsystems();
      String ourSubSystem = subSysMod.getSubSystem();
      if (moduleDependees.contains(ourSubSystem)) {
        System.out.println("의존성 존재");
      }
    }
  }
}
